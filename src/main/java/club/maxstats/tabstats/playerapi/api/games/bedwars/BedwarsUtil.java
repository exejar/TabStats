package club.maxstats.tabstats.playerapi.api.games.bedwars;

import club.maxstats.tabstats.playerapi.api.games.HGameBase;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.util.ChatColor;

public abstract class BedwarsUtil extends HGameBase {
    /* index of starWave */
    long starWave = 0;
    public BedwarsUtil(String playerName, String playerUUID) {
        super(playerName, playerUUID);
    }

    public double getFkdr(Bedwars bw) {
        return this.formatDouble(((StatInt)bw.finalKills).getValue(), ((StatInt)bw.finalDeaths).getValue());
    }

    //this is where I format all the stats and stat colors. You can modify this to your liking
    // Bedwars class is how I handle all the stats that are grabbed, you can also modify which stats are grabbed and add them to the stat list

    public ChatColor getFkdrColor(double fkdr) {
        if (fkdr < 1.5) {
            return ChatColor.GRAY;
        } else if (fkdr < 3.5) {
            return ChatColor.WHITE;
        } else if (fkdr < 5) {
            return ChatColor.GOLD;
        } else if (fkdr < 10) {
            return ChatColor.DARK_GREEN;
        } else if (fkdr < 20) {
            return ChatColor.RED;
        } else if (fkdr < 50) {
            return ChatColor.DARK_RED;
        } else if (fkdr < 100) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.DARK_PURPLE;
        }
    }

    public double getWlr(Bedwars bw) {
        return this.formatDouble(((StatInt)bw.wins).getValue(), ((StatInt)bw.losses).getValue());
    }

    public ChatColor getWlrColor(double wlr) {
        if (wlr < 1) {
            return ChatColor.GRAY;
        } else if (wlr < 2) {
            return ChatColor.WHITE;
        } else if (wlr < 3) {
            return ChatColor.GOLD;
        } else if (wlr < 5) {
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
        if (bblr < 1.5) {
            return ChatColor.GRAY;
        } else if (bblr < 2.5) {
            return ChatColor.WHITE;
        } else if (bblr < 5) {
            return ChatColor.GOLD;
        } else if (bblr < 7.5) {
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
        if (ws < 5) {
            return ChatColor.GRAY;
        } else if (ws < 10) {
            return ChatColor.WHITE;
        } else if (ws < 20) {
            return ChatColor.GOLD;
        } else if (ws < 35) {
            return ChatColor.DARK_GREEN;
        } else if (ws < 50) {
            return ChatColor.RED;
        } else if (ws < 75) {
            return ChatColor.DARK_RED;
        } else if (ws < 100) {
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
            ChatColor[] colors = new ChatColor[]{ChatColor.RED, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.AQUA, ChatColor.LIGHT_PURPLE};
            String starUnicode = "\u272B";

            /* prestige stars */
            if (star < 2000) {
                starUnicode = "\u272A";
                if (star < 1200 && star >= 1100) {
                    colors = new ChatColor[]{ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.GRAY};
                }
            } else if (star < 3000) {
                starUnicode = "\u269D";
                colors = new ChatColor[]{ChatColor.RED, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.AQUA, ChatColor.LIGHT_PURPLE};
            }

            this.starWave = (long)((System.currentTimeMillis() % 1000L / 1000.0F) * 5);

            System.out.println((int)(starWave + 0) % 4);
            System.out.println((int)(starWave + 1) % 4);
            System.out.println((int)(starWave + 2) % 4);
            System.out.println((int)(starWave + 3) % 4);
            System.out.println((int)(starWave + 4) % 4);

            return colors[(int)starWave].toString() + starString.charAt(0) + colors[(int)(starWave + 1) % 5].toString() + starString.charAt(1) + colors[(int)(starWave + 2) % 5].toString() + starString.charAt(2) + colors[(int)(starWave + 3) % 5].toString() + starString.charAt(3) + colors[(int)(starWave + 4) % 5].toString() + starUnicode;
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