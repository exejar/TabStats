package club.maxstats.tabstats.playerapi.api.games.duels;

import club.maxstats.tabstats.playerapi.api.games.HGameBase;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.util.ChatColor;

public abstract class DuelsUtil extends HGameBase {
    public DuelsUtil(String playerName, String playerUUID) {
        super(playerName, playerUUID);
    }


    //this is where I format all the stats and stat colors. You can modify this to your liking
    // Bedwars class is how I handle all the stats that are grabbed, you can also modify which stats are grabbed and add them to the stat list

    public double getWlr(Duels duels) {
        return this.formatDouble(((StatInt)duels.wins).getValue(), ((StatInt)duels.losses).getValue());
    }

    public ChatColor getWlrColor(double wlr) {
        if (wlr < 2) {
            return ChatColor.GRAY;
        } else if (wlr < 4) {
            return ChatColor.WHITE;
        } else if (wlr < 6) {
            return ChatColor.GOLD;
        } else if (wlr < 7) {
            return ChatColor.DARK_GREEN;
        } else if (wlr < 10) {
            return ChatColor.RED;
        } else if (wlr < 15) {
            return ChatColor.DARK_RED;
        } else if (wlr < 50) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public ChatColor getWSColor(double ws) {
        if (ws < 50) {
            return ChatColor.GRAY;
        } else if (ws < 200) {
            return ChatColor.WHITE;
        } else if (ws < 350) {
            return ChatColor.GOLD;
        } else if (ws < 500) {
            return ChatColor.DARK_GREEN;
        } else if (ws < 650) {
            return ChatColor.RED;
        } else if (ws < 800) {
            return ChatColor.DARK_RED;
        } else if (ws < 1000) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public ChatColor getKillsColor(int kills) {
        if (kills < 1000) {
            return ChatColor.GRAY;
        } else if (kills < 3000) {
            return ChatColor.WHITE;
        } else if (kills < 5000) {
            return ChatColor.GOLD;
        } else if (kills < 10000) {
            return ChatColor.DARK_GREEN;
        } else if (kills < 20000) {
            return ChatColor.RED;
        } else if (kills < 25000) {
            return ChatColor.DARK_RED;
        } else if (kills < 30000) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public ChatColor getWinsColor(int wins) {
        if (wins < 1000) {
            return ChatColor.GRAY;
        } else if (wins < 3000) {
            return ChatColor.WHITE;
        } else if (wins < 5000) {
            return ChatColor.GOLD;
        } else if (wins < 10000) {
            return ChatColor.DARK_GREEN;
        } else if (wins < 20000) {
            return ChatColor.RED;
        } else if (wins < 25000) {
            return ChatColor.DARK_RED;
        } else if (wins < 30000) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public ChatColor getLossesColor(int losses) {
        if (losses < 1000) {
            return ChatColor.GRAY;
        } else if (losses < 3000) {
            return ChatColor.WHITE;
        } else if (losses < 5000) {
            return ChatColor.GOLD;
        } else if (losses < 10000) {
            return ChatColor.DARK_GREEN;
        } else if (losses < 20000) {
            return ChatColor.RED;
        } else if (losses < 25000) {
            return ChatColor.DARK_RED;
        } else if (losses < 30000) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }
}