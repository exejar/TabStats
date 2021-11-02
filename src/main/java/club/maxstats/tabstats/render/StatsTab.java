package club.maxstats.tabstats.render;

import club.maxstats.tabstats.TabStats;
import club.maxstats.tabstats.playerapi.HPlayer;
import club.maxstats.tabstats.playerapi.api.games.bedwars.Bedwars;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;
import java.util.List;

public class StatsTab extends GuiPlayerTabOverlay {
    private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new StatsTab.PlayerComparator());
    private final Minecraft mc;
    private final GuiIngame guiIngame;
    private IChatComponent footer;
    private IChatComponent header;
    /** The last time the playerlist was opened (went from not being renderd, to being rendered) */
    private long lastTimeOpened;
    /** Whether or not the playerlist is currently being rendered */
    private boolean isBeingRendered;

    public StatsTab(Minecraft mcIn, GuiIngame guiIngameIn) {
        super(mcIn, guiIngameIn);
        this.mc = mcIn;
        this.guiIngame = guiIngameIn;
    }

    @Override
    public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn) {
        NetHandlerPlayClient nethandler = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> playerList = field_175252_a.sortedCopy(nethandler.getPlayerInfoMap());
        /* width of the player's name */
        int nameWidth = 0;
        /* width of the player's objective string */
        int objectiveWidth = 0;

        for (NetworkPlayerInfo playerInfo : playerList) {
            int strWidth = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(playerInfo));
            nameWidth = Math.max(nameWidth, strWidth);

            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                strWidth = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(playerInfo.getGameProfile().getName(), scoreObjectiveIn).getScoreScoreboard());
                objectiveWidth = Math.max(objectiveWidth, strWidth);
            }
        }

        /* only grabs downwards of 80 players */
        playerList = playerList.subList(0, Math.min(playerList.size(), 80));
        int playerListSize = playerList.size();

        boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        int l;

        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                l = 90;
            } else {
                l = objectiveWidth;
            }
        } else {
            l = 0;
        }

        /* the entire tab background */
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        int startingX = scaledRes.getScaledWidth() / 2 - width / 2;
        int startingY = 50;
        drawRect(startingX, startingY, scaledRes.getScaledWidth() / 2 + width / 2,  startingY + (playerListSize + 1) * 9, Integer.MIN_VALUE);
        int NAMExpos = 144;
        int STARxpos = 323;
        int WSxpos = 363;
        int FKDRxpos = 390;
        int FINALSxpos = 430;
        int WLRxpos = 480;
        int WINSxpos = 515;
        int BBLRxpos = 555;
        this.mc.fontRendererObj.drawStringWithShadow("§lNAME", NAMExpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lSTAR", STARxpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lWS", WSxpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lFKDR", FKDRxpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lFINALS", FINALSxpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lWLR", WLRxpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lWINS", WINSxpos, startingY - 9, 0xFFFFFF);
        this.mc.fontRendererObj.drawStringWithShadow("§lBBLR", BBLRxpos, startingY - 9, 0xFFFFFF);
        /* should not be like this ^
        should be like this
        this.mc.fontRendererObj.drawStringWithShadow("Name", nameX, sameY, color);
        this.mc.fontRendererObj.drawStringWithShadow("Star", starX, sameY, color);
        etc...
        And use the corresponding label X's with the stats drawn in the for loop to match the same position */

        int ySpacer = startingY;
        for (NetworkPlayerInfo networkPlayerInfo : playerList) {
            int xSpacer = startingX;
            /* entry background */
            drawRect(xSpacer, ySpacer, scaledRes.getScaledWidth() / 2 + width / 2, ySpacer + 8, 553648127);

            /* ignore this, this is just preparing the gl canvas for rendering */
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            NetworkPlayerInfo playerInfo = networkPlayerInfo;
            String name = this.getPlayerName(playerInfo);
            GameProfile gameProfile = playerInfo.getGameProfile();

            if (flag) {
                /* renders the player's face */
                EntityPlayer entityPlayer = this.mc.theWorld.getPlayerEntityByUUID(gameProfile.getId());
                boolean flag1 = entityPlayer != null && entityPlayer.isWearing(EnumPlayerModelParts.CAPE) && (gameProfile.getName().equals("Dinnerbone") || gameProfile.getName().equals("Grumm"));
                this.mc.getTextureManager().bindTexture(playerInfo.getLocationSkin());
                int u = 8 + (flag1 ? 8 : 0);
                int v = 8 * (flag1 ? -1 : 1);
                Gui.drawScaledCustomSizeModalRect(xSpacer, ySpacer, 8.0F, u, 8, v, 8, 8, 64.0F, 64.0F);

                if (entityPlayer != null && entityPlayer.isWearing(EnumPlayerModelParts.HAT)) {
                    Gui.drawScaledCustomSizeModalRect(xSpacer, ySpacer, 40.0F, u, 8, v, 8, 8, 64.0F, 64.0F);
                }

                /* adds x amount of pixels so that rendering name won't overlap with skin render */
                xSpacer += 9;
            }

            if (playerInfo.getGameType() == WorldSettings.GameType.SPECTATOR) {
                /* how you should render spectators */
            } else {
                /* how you should render everyone else */
                this.mc.fontRendererObj.drawStringWithShadow(name, xSpacer, ySpacer, -1);
            }

            if (scoreObjectiveIn != null & playerInfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
                /* how you should draw the scoreboard values of players that aren't spectators and that have score objectives */
            }

            /* spaces each entry by the specified pixels */
            ySpacer += 9;
        }
    }

    private void drawScoreboardValues(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_) {
        int i = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();

        if (p_175247_1_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            this.mc.getTextureManager().bindTexture(icons);

            if (this.lastTimeOpened == p_175247_6_.func_178855_p()) {
                if (i < p_175247_6_.func_178835_l()) {
                    p_175247_6_.func_178846_a(Minecraft.getSystemTime());
                    p_175247_6_.func_178844_b((long)(this.guiIngame.getUpdateCounter() + 20));
                } else if (i > p_175247_6_.func_178835_l()) {
                    p_175247_6_.func_178846_a(Minecraft.getSystemTime());
                    p_175247_6_.func_178844_b((long)(this.guiIngame.getUpdateCounter() + 10));
                }
            }

            if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L || this.lastTimeOpened != p_175247_6_.func_178855_p()) {
                p_175247_6_.func_178836_b(i);
                p_175247_6_.func_178857_c(i);
                p_175247_6_.func_178846_a(Minecraft.getSystemTime());
            }

            p_175247_6_.func_178843_c(this.lastTimeOpened);
            p_175247_6_.func_178836_b(i);
            int j = MathHelper.ceiling_float_int((float)Math.max(i, p_175247_6_.func_178860_m()) / 2.0F);
            int k = Math.max(MathHelper.ceiling_float_int((float)(i / 2)), Math.max(MathHelper.ceiling_float_int((float)(p_175247_6_.func_178860_m() / 2)), 10));
            boolean flag = p_175247_6_.func_178858_o() > (long)this.guiIngame.getUpdateCounter() && (p_175247_6_.func_178858_o() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;

            if (j > 0) {
                float f = Math.min((float)(p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0F);

                if (f > 3.0F) {
                    for (int l = j; l < k; ++l) {
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                    }

                    for (int j1 = 0; j1 < j; ++j1) {
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);

                        if (flag) {
                            if (j1 * 2 + 1 < p_175247_6_.func_178860_m()) {
                                this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 70, 0, 9, 9);
                            }

                            if (j1 * 2 + 1 == p_175247_6_.func_178860_m()) {
                                this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 79, 0, 9, 9);
                            }
                        }

                        if (j1 * 2 + 1 < i) {
                            this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 160 : 52, 0, 9, 9);
                        }

                        if (j1 * 2 + 1 == i) {
                            this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 169 : 61, 0, 9, 9);
                        }
                    }
                } else {
                    float f1 = MathHelper.clamp_float((float)i / 20.0F, 0.0F, 1.0F);
                    int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
                    String s = "" + (float)i / 2.0F;

                    if (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s + "hp") >= p_175247_4_) {
                        s = s + "hp";
                    }

                    this.mc.fontRendererObj.drawStringWithShadow(s, (float)((p_175247_5_ + p_175247_4_) / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2), (float)p_175247_2_, i1);
                }
            }
        } else {
            String s1 = EnumChatFormatting.YELLOW + "" + i;
            this.mc.fontRendererObj.drawStringWithShadow(s1, (float)(p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s1)), (float)p_175247_2_, 16777215);
        }
    }

    @SideOnly(Side.CLIENT)
    static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
        private PlayerComparator() {
        }

        public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
            ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
            ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
        }
    }
}