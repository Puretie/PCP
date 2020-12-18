package arrows;

import api.CustomArrow;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class IncendiaryArrow extends CustomArrow
{
    public IncendiaryArrow()
    {
        super(C.DARK_RED + "Incendiary Arrow");
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe incarrow = new ShapelessRecipe(getKey(), getItem());
        incarrow.addIngredient(Material.ARROW);
        incarrow.addIngredient(Material.BLAZE_ROD);
        return incarrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        p.getWorld().createExplosion(p.getLocation(), 1.2f, false, false);
        Location l = p.getLocation();
    }
}
