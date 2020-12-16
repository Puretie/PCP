package org.puretie.pcp.control;

import ninja.bytecode.shuriken.bukkit.bukkit.plugin.Controller;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.TippedArrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.puretie.pcp.PCP;

public class CraftingController extends Controller
{

    private NamespacedKey key = new NamespacedKey(PCP.instance, "poparrow");

    @Override
    public void start()
    {
        ShapelessRecipe poparrow = new ShapelessRecipe(key, createPopArrow());
        poparrow.addIngredient(Material.GUNPOWDER);
        poparrow.addIngredient(Material.ARROW);
        poparrow.addIngredient(Material.BLAZE_POWDER);
        Bukkit.getServer().addRecipe(poparrow);
    }

    public ItemStack createPopArrow()
    {
        ItemStack is = new ItemStack(Material.TIPPED_ARROW);
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(10010);
        im.setDisplayName(C.RED + "Explosive Arrow");
        is.setItemMeta(im);
        return is;

    }

    @Override
    public void stop()
    {
        Bukkit.getServer().removeRecipe(key);
    }

    @Override
    public void tick()
    {

    }
}
