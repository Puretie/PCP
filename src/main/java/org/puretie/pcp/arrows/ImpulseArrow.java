package org.puretie.pcp.arrows;

import org.puretie.pcp.api.CustomArrow;
import org.puretie.pcp.api.Impull;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class ImpulseArrow extends CustomArrow
{
    private double radius = 10;
    private double damage = 3;

    public ImpulseArrow()
    {
        super(C.RED + "Impulse Arrow");
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe imparrow = new ShapelessRecipe(getKey(), getItem());
        imparrow.addIngredient(Material.GUNPOWDER);
        imparrow.addIngredient(Material.ARROW);
        imparrow.addIngredient(Material.FEATHER);
        return imparrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        new Impull(radius).damage(damage).force(10, 10).punch(p.getLocation());

    }
}
