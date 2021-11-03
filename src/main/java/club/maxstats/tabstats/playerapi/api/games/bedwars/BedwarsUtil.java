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

    //this is where I format all the stats and stat colors. You can modify this to your liking
    // Bedwars class is how I handle all the stats that are grabbed, you can also modify which stats are grabbed and add them to the stat list

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

    // we need to update this o_O
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
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    /* should be the actual method used when displaying stars */
    public String getStarWithColor(int star) {
        String starString = Integer.toString(star);
        if (star < 1000) {
            return getStarColor(star) + starString + "\u272B";
        } else {
            /* if it doesn't meet any requirements, just use normal rainbow colors and star unicode */
            ChatColor[] colors = new ChatColor[]{ChatColor.RED, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.AQUA, ChatColor.LIGHT_PURPLE};;
            String starUnicode = "\u272B";

            /* prestige stars */
            if (star < 2000) {
                starUnicode = "\u272A";
                if (star < 1200) {
                    colors = new ChatColor[]{ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.GRAY};
                }
            } else if (star < 3000) {
                starUnicode = "\u269D";
                colors = new ChatColor[]{ChatColor.RED, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.AQUA, ChatColor.LIGHT_PURPLE};
            }

            return colors[0].toString() + starString.charAt(0) + colors[1].toString() + starString.charAt(1) + colors[2].toString() + starString.charAt(2) + colors[3].toString() + starString.charAt(3) + colors[4].toString() + starUnicode;
        }
    }

    public ChatColor getFinalsColor(int finals) {
        if (finals < 300) {
            return ChatColor.GRAY;
        } else if (finals < 500) {
            return ChatColor.WHITE;
        } else if (finals < 1000) {
            return ChatColor.GOLD;
        } else if (finals < 2000) {
            return ChatColor.DARK_GREEN;
        } else if (finals < 3000) {
            return ChatColor.RED;
        } else if (finals < 5000) {
            return ChatColor.DARK_RED;
        } else if (finals < 10000) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public ChatColor getWinsColor(int wins) {
        if (wins < 100) {
            return ChatColor.GRAY;
        } else if (wins < 300) {
            return ChatColor.WHITE;
        } else if (wins < 500) {
            return ChatColor.GOLD;
        } else if (wins < 1000) {
            return ChatColor.DARK_GREEN;
        } else if (wins < 2000) {
            return ChatColor.RED;
        } else if (wins < 2500) {
            return ChatColor.DARK_RED;
        } else if (wins < 3000) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }
}