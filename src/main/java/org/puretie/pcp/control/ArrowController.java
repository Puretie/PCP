package org.puretie.pcp.control;

import api.CustomArrow;
import arrows.ConcussiveArrow;
import arrows.ConductiveArrow;
import arrows.EnderArrow;
import arrows.ImpulseArrow;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.Controller;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.JarScannerSpecial;
import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Registry;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.puretie.pcp.PCP;

import java.io.IOException;

public class ArrowController extends Controller
{
    private KList<CustomArrow> registry = new KList<>();
    @Override
    public void start()
    {
        registry.add(new ConcussiveArrow());
        registry.add(new ConductiveArrow());
        registry.add(new ImpulseArrow());
        registry.add(new EnderArrow());
        for(CustomArrow i : registry)
        {
            i.register();
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void on(ProjectileLaunchEvent e)
    {
        if(e.getEntity().getShooter() instanceof Player)
        {
            Player p = (Player) e.getEntity().getShooter();
            if(e.getEntity() instanceof AbstractArrow)
            {
                ItemStack arrow = getNextArrow(p);
                if(arrow != null)
                {
                    CustomArrow c = getType(arrow);
                    if(c != null)
                    {
                        ItemStack template = c.getItem();
                        if(arrow.getType().equals(template.getType()))
                        {
                            if(arrow.getItemMeta().getCustomModelData() == template.getItemMeta().getCustomModelData())
                            {
                                c.getAirborneEntities().add(e.getEntity().getEntityId());
                                c.fire(e.getEntity());
                            }
                        }
                    }

                }
            }
        }
    }

    public CustomArrow getType(ItemStack is)
    {
        for(CustomArrow i : registry)
        {
            if(i.getId() == is.getItemMeta().getCustomModelData())
            {
                return i;
            }
        }
        return null;
    }

    @EventHandler
    public void on(ProjectileHitEvent e)
    {
        for(CustomArrow i : registry)
        {
            if(i.getAirborneEntities().remove((Integer)e.getEntity().getEntityId()))
            {
                i.hit(e.getEntity());
                return;
            }
        }

    }

    public ItemStack getNextArrow(Player p)
    {
        ItemStack[] inv = p.getInventory().getContents();
        for(int i = 0; i < inv.length; i++)
        {
            ItemStack item = inv[i];
            if(item == null)
            {
                continue;
            }
                switch (item.getType())
                {
                    case ARROW:
                    case SPECTRAL_ARROW:
                    case LEGACY_ARROW:
                    case LEGACY_SPECTRAL_ARROW:
                    case LEGACY_TIPPED_ARROW:
                    case TIPPED_ARROW:
                        return item;
                }
            w("Uncaught arrow type:" + item.getType());
        }
        return null;
    }

    @Override
    public void stop()
    {
        for(CustomArrow i : registry)
        {
            i.unregister();
        }
    }

    @Override
    public void tick()
    {

    }
}
