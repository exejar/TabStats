package club.maxstats.tabstats.render;

import club.maxstats.tabstats.TabStats;
import club.maxstats.tabstats.playerapi.HPlayer;
import club.maxstats.tabstats.playerapi.api.stats.Stat;
import club.maxstats.tabstats.playerapi.api.stats.StatDouble;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.playerapi.api.stats.StatString;
import club.maxstats.tabstats.util.ChatColor;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Comparator;
import java.util.List;

/* fair warning, this class is heavily "documented" (very poorly) for new programmers to understand how it works */
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
    private final int entryHeight = 12;
    private final int backgroundBorderSize = 12;
    public static final int headSize = 12;

    public StatsTab(Minecraft mcIn, GuiIngame guiIngameIn) {
        super(mcIn, guiIngameIn);
        this.mc = mcIn;
        this.guiIngame = guiIngameIn;
    }

    public void renderNewPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn, List<Stat> gameStatTitleList, String gamemode) {
        NetHandlerPlayClient nethandler = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> playerList = field_175252_a.sortedCopy(nethandler.getPlayerInfoMap());
        /* width of the player's name */
        int nameWidth = 0;
        /* width of the player's objective string */
        int objectiveWidth = 0;
        /* retrieve scaled resolution for accurate dimensions */
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        /* where the render should start on x plane */
        int startingX = scaledRes.getScaledWidth() / 2 - width / 2;
        /* where the render should start on y plane */
        int startingY = 35;

        /* this is kind of useless...as nameWidth and objectiveWidth aren't used */
        for (NetworkPlayerInfo playerInfo : playerList) {
            int strWidth = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(playerInfo));
            nameWidth = Math.max(nameWidth, strWidth);

            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                strWidth = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(playerInfo.getGameProfile().getName(), scoreObjectiveIn).getScoreScoreboard());
                objectiveWidth = Math.max(objectiveWidth, strWidth);
            }
        }

        /* initialize objectiveName outside of the below if block, so we can render it after the background */
        String objectiveName = "";
        /* meant for initializing the render of score objective */
        if (scoreObjectiveIn != null) {
            /* this is usually the raw name meant for internal usage */
            String objectiveRawName = WordUtils.capitalize(scoreObjectiveIn.getName().replace("_", " "));

            /* this is usually the formatted name meant for display */
            String objectiveDisplayname = WordUtils.capitalize(scoreObjectiveIn.getDisplayName().replace("_", ""));

            /* move starting X back based on length of rendered score objective */
            startingX -= (10 + this.mc.fontRendererObj.getStringWidth(objectiveDisplayname));
            /* you can change this value to objectiveRawName, but I like using the displayname */
            objectiveName = objectiveDisplayname;
        }

        /* only grabs downwards of 80 players */
        playerList = playerList.subList(0, Math.min(playerList.size(), 80));
        int playerListSize = playerList.size();

        /* the entire tab background */
        drawRect(startingX - this.backgroundBorderSize - (objectiveName.isEmpty() ? 0 : 5 + this.mc.fontRendererObj.getStringWidth(objectiveName)), startingY - this.backgroundBorderSize, (scaledRes.getScaledWidth() / 2 + width / 2) + this.backgroundBorderSize,  (startingY + (playerListSize + 1) * (this.entryHeight + 1) - 1) + this.backgroundBorderSize, Integer.MIN_VALUE);

        /* draw an entry rect for the stat name title */
        drawRect(startingX, startingY, scaledRes.getScaledWidth() / 2 + width / 2, startingY + this.entryHeight, 553648127);

        /* Start with drawing the name and objective, as they will always be here and aren't inside of the Stat List */
        int statXSpacer = startingX + headSize + 2;
        this.mc.fontRendererObj.drawStringWithShadow(ChatColor.BOLD + "NAME", statXSpacer, startingY + (this.entryHeight / 2 - 4), ChatColor.WHITE.getRGB());
        this.mc.fontRendererObj.drawStringWithShadow(objectiveName, startingX - (this.mc.fontRendererObj.getStringWidth(objectiveName) + 5), startingY + (this.entryHeight / 2 - 4), ChatColor.WHITE.getRGB());

        /* adds 140 pixels to statXSpacer since name's are way longer than stats */
        statXSpacer += 140;

        /* loops through all the stats that should be displayed and renders their stat titles */
        for (Stat stat : gameStatTitleList) {
            String statName = stat.getStatName();
            this.mc.fontRendererObj.drawStringWithShadow(ChatColor.BOLD + statName, statXSpacer, startingY + (this.entryHeight / 2 - 4), ChatColor.WHITE.getRGB());

            /* adds spacer for next stat */
            statXSpacer += this.mc.fontRendererObj.getStringWidth(ChatColor.BOLD + statName) + 10;
        }

        /* add entryHeight so it starts below the stat name title */
        int ySpacer = startingY + this.entryHeight + 1;
        for (NetworkPlayerInfo playerInfo : playerList) {
            int xSpacer = startingX;
            /* entry background */
            drawRect(xSpacer, ySpacer, scaledRes.getScaledWidth() / 2 + width / 2, ySpacer + this.entryHeight, 553648127);

            /* ignore this, this is just preparing the gl canvas for rendering */
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            String name = this.getPlayerName(playerInfo);
            GameProfile gameProfile = playerInfo.getGameProfile();

            boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
            if (flag) {
                /* renders the player's face */
                EntityPlayer entityPlayer = this.mc.theWorld.getPlayerEntityByUUID(gameProfile.getId());
                boolean flag1 = entityPlayer != null && entityPlayer.isWearing(EnumPlayerModelParts.CAPE) && (gameProfile.getName().equals("Dinnerbone") || gameProfile.getName().equals("Grumm"));
                this.mc.getTextureManager().bindTexture(playerInfo.getLocationSkin());
                int u = 8 + (flag1 ? 8 : 0);
                int v = 8 * (flag1 ? -1 : 1);
                Gui.drawScaledCustomSizeModalRect(xSpacer, ySpacer, 8.0F, u, 8, v, headSize, headSize, 64.0F, 64.0F);

                if (entityPlayer != null && entityPlayer.isWearing(EnumPlayerModelParts.HAT)) {
                    Gui.drawScaledCustomSizeModalRect(xSpacer, ySpacer, 40.0F, u, 8, v, headSize, headSize, 64.0F, 64.0F);
                }

                /* adds x amount of pixels so that rendering name won't overlap with skin render */
                xSpacer += headSize + 2;
            }

            if (playerInfo.getGameType() == WorldSettings.GameType.SPECTATOR) {
                /* how you should render spectators */
            } else {
                /* how you should render everyone else */
                HPlayer hPlayer = TabStats.getTabStats().getStatWorld().getPlayerByUUID(gameProfile.getId());
                if (hPlayer != null) {
                    /* render tabstats here */
                    name = hPlayer.getPlayerRank() + ChatColor.RESET + (name.contains(ChatColor.OBFUSCATE.toString()) ? hPlayer.getPlayerRankColor() + hPlayer.getPlayerName() : name);

                    /* gets bedwars if the gamemode is not a game added to the hplayer's game list, otherwise, grab the game stats based on the scoreboard */
                    List<Stat> statList = hPlayer.getFormattedGameStats(gamemode) == null ? hPlayer.getFormattedGameStats("BEDWARS") : hPlayer.getFormattedGameStats(gamemode);
                    /* start at the first stat */
                    int valueXSpacer = startingX + 140 + headSize + 2;

                    for (Stat stat : statList) {
                        String statValue = "";

                        /* finds the exact stat type so it can properly retrieve the stat value */
                        switch (stat.getType()) {
                            case INT:
                                statValue = Integer.toString(((StatInt)stat).getValue());
                                break;
                            case DOUBLE:
                                statValue = Double.toString(((StatDouble)stat).getValue());
                                break;
                            case STRING:
                                statValue = ((StatString)stat).getValue();
                                break;
                        }

                        // draws the stats
                        this.mc.fontRendererObj.drawStringWithShadow(statValue, valueXSpacer, ySpacer + (this.entryHeight / 2 - 4), ChatColor.WHITE.getRGB());
                        valueXSpacer += this.mc.fontRendererObj.getStringWidth(ChatColor.BOLD + stat.getStatName()) + 10;
                    }
                }

                // draws the players name
                this.mc.fontRendererObj.drawStringWithShadow(name, xSpacer, ySpacer + (this.entryHeight / 2 - 4), -1);
            }

            if (scoreObjectiveIn != null & playerInfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
                /* if player isn't a spectator and scoreobjective isn't null, render their score objective */

                /* not really sure how all objectives are drawn, but I understand HP and that's usually what Hypixel uses lol */
                this.drawScoreboardValues(scoreObjectiveIn, ySpacer, gameProfile.getName(), xSpacer, startingX - 5, playerInfo);
            }

            /* spaces each entry by the specified pixels */
            ySpacer += this.entryHeight + 1;
        }
    }

    private void drawScoreboardValues(ScoreObjective objectiveIn, int y, String playerName, int startX, int endX, NetworkPlayerInfo playerInfo) {
        int i = objectiveIn.getScoreboard().getValueFromObjective(playerName, objectiveIn).getScorePoints();

        if (objectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            this.mc.getTextureManager().bindTexture(icons);

            if (this.lastTimeOpened == playerInfo.func_178855_p()) {
                if (i < playerInfo.func_178835_l()) {
                    playerInfo.func_178846_a(Minecraft.getSystemTime());
                    playerInfo.func_178844_b((long)(this.guiIngame.getUpdateCounter() + 20));
                } else if (i > playerInfo.func_178835_l()) {
                    playerInfo.func_178846_a(Minecraft.getSystemTime());
                    playerInfo.func_178844_b((long)(this.guiIngame.getUpdateCounter() + 10));
                }
            }

            if (Minecraft.getSystemTime() - playerInfo.func_178847_n() > 1000L || this.lastTimeOpened != playerInfo.func_178855_p()) {
                playerInfo.func_178836_b(i);
                playerInfo.func_178857_c(i);
                playerInfo.func_178846_a(Minecraft.getSystemTime());
            }

            playerInfo.func_178843_c(this.lastTimeOpened);
            playerInfo.func_178836_b(i);
            int j = MathHelper.ceiling_float_int((float)Math.max(i, playerInfo.func_178860_m()) / 2.0F);
            int k = Math.max(MathHelper.ceiling_float_int((float)(i / 2)), Math.max(MathHelper.ceiling_float_int((float)(playerInfo.func_178860_m() / 2)), 10));
            boolean flag = playerInfo.func_178858_o() > (long)this.guiIngame.getUpdateCounter() && (playerInfo.func_178858_o() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;

            if (j > 0) {
                float f = Math.min((float)(endX - startX - 4) / (float)k, 9.0F);

                if (f > 3.0F) {
                    for (int l = j; l < k; ++l) {
                        this.drawTexturedModalRect((float)startX + (float)l * f, (float)y, flag ? 25 : 16, 0, 9, 9);
                    }

                    for (int j1 = 0; j1 < j; ++j1) {
                        this.drawTexturedModalRect((float)startX + (float)j1 * f, (float)y, flag ? 25 : 16, 0, 9, 9);

                        if (flag) {
                            if (j1 * 2 + 1 < playerInfo.func_178860_m()) {
                                this.drawTexturedModalRect((float)startX + (float)j1 * f, (float)y, 70, 0, 9, 9);
                            }

                            if (j1 * 2 + 1 == playerInfo.func_178860_m()) {
                                this.drawTexturedModalRect((float)startX + (float)j1 * f, (float)y, 79, 0, 9, 9);
                            }
                        }

                        if (j1 * 2 + 1 < i) {
                            this.drawTexturedModalRect((float)startX + (float)j1 * f, (float)y, j1 >= 10 ? 160 : 52, 0, 9, 9);
                        }

                        if (j1 * 2 + 1 == i) {
                            this.drawTexturedModalRect((float)startX + (float)j1 * f, (float)y, j1 >= 10 ? 169 : 61, 0, 9, 9);
                        }
                    }
                } else {
                    float f1 = MathHelper.clamp_float((float)i / 20.0F, 0.0F, 1.0F);
                    int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
                    String s = "" + (float)i / 2.0F;

                    if (endX - this.mc.fontRendererObj.getStringWidth(s + "hp") >= startX) {
                        s = s + "hp";
                    }

                    this.mc.fontRendererObj.drawStringWithShadow(s, (float)((endX + startX) / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2), (float)y, i1);
                }
            }
        } else {
            /* This is where Hypixel usually has Client draw Scoreboard Stats */

            String s1 = EnumChatFormatting.YELLOW + "" + i;
            this.mc.fontRendererObj.drawStringWithShadow(s1, (float)(endX - this.mc.fontRendererObj.getStringWidth(s1)), (float)y + (this.entryHeight / 2 - 4), 16777215);
//            drawRect(endX - this.mc.fontRendererObj.getStringWidth(objectiveIn.getDisplayName()), y, endX, y + this.entryHeight, 553648127);
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