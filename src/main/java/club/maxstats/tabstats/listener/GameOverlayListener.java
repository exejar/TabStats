package club.maxstats.tabstats.listener;

import club.maxstats.tabstats.TabStats;
import club.maxstats.tabstats.playerapi.HPlayer;
import club.maxstats.tabstats.playerapi.api.stats.Stat;
import club.maxstats.tabstats.render.StatsTab;
import club.maxstats.tabstats.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class GameOverlayListener {
    private StatsTab statsTab;
    private Minecraft mc = Minecraft.getMinecraft();

    public GameOverlayListener() {
        this.statsTab = new StatsTab(this.mc, this.mc.ingameGUI);
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
        /* checking if the event is rendering player list, if it is, cancel the current render and render the new overlay */
        if (event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST) {
            event.setCanceled(true);
            Scoreboard scoreboard = this.mc.thePlayer.getWorldScoreboard();
            /* scoreboard.getObjectiveInDisplaySlot(0) gets the main score objective */
            /* this is where we render our new tab */

            /* Sets gamemode based off the name of the scoreboard */
            String gamemode = "BEDWARS";
            if (scoreboard.getObjectiveInDisplaySlot(1) != null) {
                gamemode = ChatColor.stripColor(scoreboard.getObjectiveInDisplaySlot(1).getDisplayName()).replace(" ", "");
            }

            /* your HPlayer object */
            HPlayer theHPlayer = TabStats.getTabStats().getStatWorld().getPlayerByUUID(Minecraft.getMinecraft().thePlayer.getUniqueID());

            List<Stat> gameStatTitleList = new ArrayList<>();
            if (theHPlayer != null) {
                gameStatTitleList = theHPlayer.getFormattedGameStats(gamemode);
            }

            /* the starting x amount before we start rendering stats, add to both sides */
            int width = (StatsTab.headSize + 2) * 2 + this.mc.fontRendererObj.getStringWidth(ChatColor.BOLD + "[YOUTUBE] WWWWWWWWWWWWWWWW") + 10 - 10;

            /* kind of a really bad way of doing this, as we loop through this same stat list again inside of the renderNewPlayerlist method */
            for (Stat stat : gameStatTitleList) {
                width += this.mc.fontRendererObj.getStringWidth(ChatColor.BOLD + stat.getStatName()) + 10;
            }

            this.statsTab.renderNewPlayerlist(width, scoreboard, scoreboard.getObjectiveInDisplaySlot(0), gameStatTitleList, gamemode);
        }
    }
}
