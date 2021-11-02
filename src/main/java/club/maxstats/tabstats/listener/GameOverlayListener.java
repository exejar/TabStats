package club.maxstats.tabstats.listener;

import club.maxstats.tabstats.render.StatsTab;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
            statsTab.renderPlayerlist(0, scoreboard, scoreboard.getObjectiveInDisplaySlot(0));
        }
    }
}
