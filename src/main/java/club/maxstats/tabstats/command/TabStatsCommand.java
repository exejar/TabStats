package club.maxstats.tabstats.command;

import club.maxstats.tabstats.TabStats;
import club.maxstats.tabstats.util.Multithreading;
import club.maxstats.tabstats.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.concurrent.*;

public class TabStatsCommand extends CommandBase {

    public String getCommandName() {
        return References.MODID;
    }

    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public void processCommand(ICommandSender sender, String[] args) {
        Multithreading.schedule(() -> Minecraft.getMinecraft().displayGuiScreen(TabStats.getTabStats().getConfig().gui()), 100, TimeUnit.MILLISECONDS);
    }
}