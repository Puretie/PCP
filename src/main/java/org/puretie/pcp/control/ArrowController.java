package org.puretie.pcp.control;


import com.google.gson.Gson;
import lombok.Getter;
import lombok.SneakyThrows;
import ninja.bytecode.shuriken.bukkit.plugin.Controller;
import ninja.bytecode.shuriken.bukkit.plugin.JarScannerSpecial;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.io.FolderWatcher;
import ninja.bytecode.shuriken.io.IO;
import ninja.bytecode.shuriken.json.JSONObject;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.puretie.pcp.PCP;
import org.puretie.pcp.api.CustomArrow;

import java.io.File;

public class ArrowController extends Controller
{
    private FolderWatcher fw;
    @Getter
    private KList<CustomArrow> registry = new KList<>();
    @Override
    public void start()
    {
        setTickRate(20);
        load();
    }

    @SneakyThrows
    private void load()
    {
        for(CustomArrow i : registry)
        {
            i.unregister();
        }
        registry.clear();
        JarScannerSpecial s = new JarScannerSpecial(PCP.instance.getJar(), "org.puretie.pcp.arrows");
        s.scan();
        s.getClasses().forEach((i) -> {
            try
            {
                File config = PCP.instance.getDataFile("config", i.getSimpleName().toLowerCase().replaceAll("\\Qarrow\\E", "") + ".json");
                CustomArrow temp = (CustomArrow) i.getConstructor().newInstance();
                if(!config.exists())
                {
                    IO.writeAll(config, new JSONObject(new Gson().toJson(temp)).toString(4));
                }
                CustomArrow c = new Gson().fromJson(IO.readAll(config), (Class<? extends CustomArrow>) i);
                c.register();
                registry.add(c);
            } catch (Throwable e)
            {
                e.printStackTrace();
            }
        });
        fw = new FolderWatcher(PCP.instance.getDataFolder("config"));
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
                i.hit(e.getEntity(), e);
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
        if(fw.checkModified()){
            load();
            l("Configs Hotloaded... Like magic!");
        }
    }
}
