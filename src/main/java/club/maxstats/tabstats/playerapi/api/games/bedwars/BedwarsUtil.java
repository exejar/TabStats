package club.maxstats.tabstats.playerapi.api.games.bedwars;

import club.maxstats.tabstats.playerapi.api.games.HGameBase;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.util.ChatColor;

public abstract class BedwarsUtil extends HGameBase {
    public BedwarsUtil(String playerName, String playerUUID) {
        super(playerName, playerUUID);
    }

    public double getFkdr(Bedwars bw) {
        return this.formatDouble(((StatInt)bw.finalKills).getValue(), ((StatInt)bw.finalDeaths).getValue());
    }

    public ChatColor getFkdrColor(double fkdr) {
        if (fkdr < 5) {
            return ChatColor.GRAY;
        } else if (fkdr < 10) {
            return ChatColor.WHITE;
        } else if (fkdr < 20) {
            return ChatColor.GOLD;
        } else if (fkdr < 35) {
            return ChatColor.DARK_GREEN;
        } else if (fkdr < 60) {
            return ChatColor.RED;
        } else if (fkdr < 100) {
            return ChatColor.DARK_RED;
        } else if (fkdr < 500) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public double getWlr(Bedwars bw) {
        return this.formatDouble(((StatInt)bw.wins).getValue(), ((StatInt)bw.losses).getValue());
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

    public double getBblr(Bedwars bw) {
        return this.formatDouble(((StatInt)bw.bedsBroken).getValue(), ((StatInt)bw.bedsLost).getValue());
    }

    public ChatColor getBblrColor(double bblr) {
        if (bblr < 2) {
            return ChatColor.GRAY;
        } else if (bblr < 4) {
            return ChatColor.WHITE;
        } else if (bblr < 6) {
            return ChatColor.GOLD;
        } else if (bblr < 7) {
            return ChatColor.DARK_GREEN;
        } else if (bblr < 10) {
            return ChatColor.RED;
        } else if (bblr < 15) {
            return ChatColor.DARK_RED;
        } else if (bblr < 50) {
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

    public ChatColor getStarColor(int star) {
        if (star < 100) {
            return ChatColor.GRAY;
        } else if (star < 200) {
            return ChatColor.WHITE;
        } else if (star < 300) {
            return ChatColor.GOLD;
        } else if (star < 400) {
            return ChatColor.AQUA;
        } else if (star < 500) {
            return ChatColor.DARK_GREEN;
        } else if (star < 600) {
            return ChatColor.DARK_AQUA;
        } else if (star < 700) {
            return ChatColor.DARK_RED;
        } else if (star < 800) {
            return ChatColor.LIGHT_PURPLE;
        } else if (star < 900) {
            return ChatColor.BLUE;
        } else if (star < 1000) {
            return ChatColor.DARK_PURPLE;
        } else {
            return ChatColor.RED;
        }
    }
}
