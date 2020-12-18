package org.puretie.pcp;


import lombok.Getter;
import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.plugin.Control;
import ninja.bytecode.shuriken.bukkit.plugin.Instance;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenPlugin;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import ninja.bytecode.shuriken.bukkit.util.text.TXT;
import org.puretie.pcp.commands.CommandPCP;
import org.puretie.pcp.control.ArrowController;
import org.puretie.pcp.control.CraftingController;

import java.io.File;


public class PCP extends ShurikenPlugin
{

    @Command
    private CommandPCP pcp;
    public File getJar()
    {
        return getFile();
    }
    @Instance
    public static PCP instance;

    @Control
    @Getter
    private CraftingController crafting;
    @Control
    @Getter
    private ArrowController arrow;

    @Override
    public void start()
    {
        l("Some text here");
    }

    @Override
    public void stop()
    {

    }

    @Override
    public String getTag(String subTag)
    {
        return TXT.makeTag(C.DARK_GRAY, C.BLUE, C.DARK_GRAY, C.GRAY, "PCP");
    }
}
