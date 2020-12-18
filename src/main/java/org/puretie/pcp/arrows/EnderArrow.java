package arrows;


import api.CustomArrow;
import ninja.bytecode.shuriken.bukkit.sched.SR;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.Vector;
import ninja.bytecode.shuriken.bukkit.util.text.C;

public class EnderArrow extends CustomArrow
{
    public EnderArrow()
    {
        super(C.DARK_PURPLE + "Ender Arrow");
    }

    @Override
    public ItemStack buildItem()
    {
        return new ItemStack(Material.TIPPED_ARROW);
    }

    @Override
    public Recipe buildRecipe()
    {
        ShapelessRecipe endarrow = new ShapelessRecipe(getKey(), getItem());
        endarrow.addIngredient(Material.ENDER_EYE);
        endarrow.addIngredient(Material.ARROW);
        return endarrow;
    }

    @Override
    public void fire(Projectile p)
    {
        if(p.getShooter() instanceof Entity)
        {
            if(p.getShooter() instanceof Player)
            {
                ArmorStand a = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                a.setArms(false);
                a.setMarker(true);
                a.setVisible(false);
                p.addPassenger(a);
                GameMode g = ((Player) p.getShooter()).getGameMode();
                ((Player) p.getShooter()).setGameMode(GameMode.SPECTATOR);
                ((Player) p.getShooter()).setSpectatorTarget(a);
                new SR(0)
                {
                    @Override
                    public void run()
                    {
                        if(getAirborneEntities().contains(p.getEntityId()))
                        {
                            Vector v = p.getVelocity().normalize();
                            Location l = p.getLocation().clone();
                            l.setDirection(v);
                            a.teleport(l);
                        }
                        else
                        {
                            cancel();
                            ((Player) p.getShooter()).setGameMode(g);
                            a.remove();
                        }
                    }
                };
            }

        }
    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        if(p.getShooter() instanceof Entity)
        {
            ((Entity) p.getShooter()).teleport(p.getLocation());
        }
    }
}
