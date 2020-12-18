package org.puretie.pcp.arrows;

import org.puretie.pcp.api.CustomArrow;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class ConductiveArrow extends CustomArrow
{
    public ConductiveArrow()
    {
        super(C.GOLD + "Conductive Arrow");
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe condarrow = new ShapelessRecipe(getKey(), getItem());
        condarrow.addIngredient(Material.CHAIN);
        condarrow.addIngredient(Material.ARROW);
        condarrow.addIngredient(Material.IRON_INGOT);
        return condarrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        p.getWorld().strikeLightning(p.getLocation());
    }
}
