package arrows;

import api.CustomArrow;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class ConcussiveArrow extends CustomArrow
{
    public ConcussiveArrow()
    {
        super(C.YELLOW + "Concussive Arrow");
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
        concarrow.addIngredient(Material.GUNPOWDER);
        concarrow.addIngredient(Material.ARROW);
        concarrow.addIngredient(Material.BLAZE_POWDER);
        return concarrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        p.getWorld().createExplosion(p.getLocation(), 2.4f, false, false);
    }
}
