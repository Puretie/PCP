package api;

import lombok.Data;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.puretie.pcp.PCP;

@Data
public abstract class CustomArrow
{
    private final KList<Integer> airborneEntities = new KList<>();
    private final String name;
    private final int id;
    private static int _id = 13377;
    public CustomArrow(String name)
    {
        this.name = name;
        this.id = _id++;
    }
    public NamespacedKey getKey(){
        return new NamespacedKey((Plugin) PCP.instance, getRegisteredName());

    }
    public String getRegisteredName(){
        return C.stripColor(name.toLowerCase()).replaceAll("\\Q \\E", "");
    }

    public void register(){
        Bukkit.getServer().addRecipe(buildRecipe());
    };
    public void unregister(){
        Bukkit.getServer().removeRecipe(getKey());
    };
    public abstract ItemStack buildItem();
    public ItemStack getItem()
    {
        ItemStack is = buildItem();
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(getId());
        im.setDisplayName(getName());
        is.setItemMeta(im);
        return is;
    }
    public abstract Recipe buildRecipe();
    public abstract void fire(Projectile p);
    public abstract void hit(Projectile p, ProjectileHitEvent e);
}
