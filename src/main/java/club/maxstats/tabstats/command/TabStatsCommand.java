package club.maxstats.tabstats.command;

import club.maxstats.tabstats.TabStats;
import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

public class TabStatsCommand extends Command {
    public TabStatsCommand() {
        super ("tabstats", true);
    }

    @DefaultHandler
    public void handle() {
        EssentialAPI.getGuiUtil().openScreen(TabStats.config.gui());
    }

}
