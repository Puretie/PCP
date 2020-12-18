package org.puretie.pcp.arrows;

import ninja.bytecode.shuriken.bukkit.sched.J;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import ninja.bytecode.shuriken.math.M;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.puretie.pcp.api.CustomArrow;

public class IncendiaryArrow extends CustomArrow
{
    private double radius = 8;

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

    public boolean canHoldFire(Block b)
    {
        if(b.getType().isAir())
        {
            return false;
        }
        return b.getType().isSolid();
    }

    @Override
    public void hit(Projectile p, ProjectileHitEvent e)
    {
        Location l = p.getLocation();
        int cx = l.getBlockX();
        int cy = l.getBlockY();
        int cz = l.getBlockZ();
        double d = radius * radius;
        for(int x = (int) (l.getBlockX() - radius); x < l.getBlockX() + radius; x++)
        {
            for(int y = (int) (l.getBlockY() - radius); y < l.getBlockY() + radius; y++)
            {
                for(int z = (int) (l.getBlockZ() - radius); z < l.getBlockZ() + radius; z++)
                {
                    double dist = Math.abs(Math.pow(cx - x, 2) + Math.pow(cy - y, 2) + Math.pow(cz - z, 2));
                    if(dist <= d)
                    {
                        double chance = 1d - (dist/d);
                        chance *= 0.6;
                        if(M.r(chance))
                        {
                            Block b = l.getWorld().getBlockAt(x,y,z);
                            if(b.getType().isAir())
                            {
                                Fire f = (Fire) Material.FIRE.createBlockData();
                                boolean valid = false;
                                if(canHoldFire(b.getRelative(BlockFace.NORTH)))
                                {
                                    f.setFace(BlockFace.NORTH, true);
                                    valid = true;
                                }

                                if(canHoldFire(b.getRelative(BlockFace.EAST)))
                                {
                                    f.setFace(BlockFace.EAST, true);
                                    valid = true;
                                }

                                if(canHoldFire(b.getRelative(BlockFace.SOUTH)))
                                {
                                    f.setFace(BlockFace.SOUTH, true);
                                    valid = true;
                                }

                                if(canHoldFire(b.getRelative(BlockFace.WEST)))
                                {
                                    f.setFace(BlockFace.WEST, true);
                                    valid = true;
                                }

                                if(canHoldFire(b.getRelative(BlockFace.UP)))
                                {
                                    f.setFace(BlockFace.UP, true);
                                    valid = true;
                                }
                                if(canHoldFire(b.getRelative(BlockFace.DOWN)))
                                {
                                    valid = true;
                                }
                                if(valid)
                                {
                                    J.s(() ->  b.setBlockData(f, true), (int) ((1d - chance) * radius));
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
