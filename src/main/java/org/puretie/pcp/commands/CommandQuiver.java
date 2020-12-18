package org.puretie.pcp.commands;

import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.puretie.pcp.PCP;
import org.puretie.pcp.api.CustomArrow;

public class CommandQuiver extends ShurikenCommand
{
    public CommandQuiver()
    {
        super("quiver", "quiv", "q", "inventory", "i");
    }

    @Override
    public boolean handle(ShurikenSender sender, String[] args)
    {
        Inventory i = Bukkit.createInventory(null, InventoryType.CHEST, "Quiver");
        for(CustomArrow c : PCP.instance.getArrow().getRegistry())
        {
            ItemStack is = c.getItem();
            is.setAmount(64);
            i.addItem(is);
        }
        sender.player().openInventory(i);
        return true;
    }
}
