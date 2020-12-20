package org.puretie.pcp.arrows;

import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.puretie.pcp.api.CustomArrow;

public class LevitatingArrow extends CustomArrow
{
    private int amp = 1;
    public int durationInSeconds = 3;

    public LevitatingArrow()
    {
        super(C.DARK_GRAY + "Levitating Arrow");
    }

    public int getDurationInSeconds()
    {
        return (durationInSeconds * 20);
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe criparrow = new ShapelessRecipe(getKey(), getItem());
        criparrow.addIngredient(Material.GUNPOWDER);
        criparrow.addIngredient(Material.ARROW);
        criparrow.addIngredient(Material.BLAZE_POWDER);
        return criparrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        if(e.getHitEntity() instanceof LivingEntity)
        {
            ((LivingEntity) e.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, getDurationInSeconds(), amp));

        }

    }
}
