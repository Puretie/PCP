package org.puretie.pcp.commands;

import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;

public class CommandPCP extends ShurikenCommand
{
    @Command
    private CommandQuiver q;
    public CommandPCP()
    {
        super("pcp", "arrows", "arrow");
    }

    @Override
    public boolean handle(ShurikenSender sender, String[] args)
    {
        return true;
    }
}
