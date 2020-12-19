package org.puretie.pcp.arrows;

import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.puretie.pcp.api.CustomArrow;

import java.util.List;

public class SilenceArrow extends CustomArrow
{
    private double radius = 8;

    public SilenceArrow()
    {
        super(C.BLACK + "Silence Arrow");
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe concarrow = new ShapelessRecipe(getKey(), getItem());
        concarrow.addIngredient(Material.CLAY_BALL);
        concarrow.addIngredient(Material.ARROW);
        concarrow.addIngredient(Material.SAND);
        return concarrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        if(e.getHitEntity() != null)
        {
            double xx = e.getHitEntity().getLocation().getBlockX();
            double yy = e.getHitEntity().getLocation().getBlockY();
            double zz = e.getHitEntity().getLocation().getBlockZ();
            List<Entity> ez = e.getHitEntity().getNearbyEntities(xx,yy,zz);
            e.getHitEntity().setSilent(true);

            for(int i = 0; i < ez.size(); i++)
            {
                ez.get(i).setSilent(true);
            }
        }
    }
}
