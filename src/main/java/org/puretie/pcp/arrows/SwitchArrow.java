package arrows;

import api.CustomArrow;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;


public class SwitchArrow extends CustomArrow
{
    public SwitchArrow()
    {
        super(C.DARK_AQUA + "Switch Arrow");
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe swarrow = new ShapelessRecipe(getKey(), getItem());
        swarrow.addIngredient(Material.ENDER_PEARL);
        swarrow.addIngredient(Material.ARROW);
        swarrow.addIngredient(Material.SPIDER_EYE);
        return swarrow;
    }

    @Override
    public void fire(Projectile p)
    {

    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        if(p.getShooter() instanceof Entity)
        {
            Entity pl = (Entity) p.getShooter();
            if(e.getHitEntity() != null)
            {
                Location pll = pl.getLocation().clone();
                Location ell = e.getEntity().getLocation().clone();
                e.getHitEntity().teleport(pll);
                pl.teleport(ell);

            }
        }
    }
}
