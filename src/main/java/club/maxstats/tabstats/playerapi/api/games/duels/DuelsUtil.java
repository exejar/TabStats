package club.maxstats.tabstats.playerapi.api.games.duels;

import club.maxstats.tabstats.playerapi.api.games.HGameBase;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.playerapi.api.stats.StatString;
import club.maxstats.tabstats.util.ChatColor;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;

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

    public ChatColor getWSColor(int ws) {
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
    public ChatColor getBestWSColor(int bws) {
        if (bws < 50) {
            return ChatColor.GRAY;
        } else if (bws < 200) {
            return ChatColor.WHITE;
        } else if (bws < 350) {
            return ChatColor.GOLD;
        } else if (bws < 500) {
            return ChatColor.DARK_GREEN;
        } else if (bws < 650) {
            return ChatColor.RED;
        } else if (bws < 800) {
            return ChatColor.DARK_RED;
        } else if (bws < 1000) {
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
        if (wins < 51) {
            return ChatColor.GRAY;
        } else if (wins < 101) {
            return ChatColor.WHITE;
        } else if (wins < 251) {
            return ChatColor.GOLD;
        } else if (wins < 501) {
            return ChatColor.DARK_AQUA;
        } else if (wins < 1001) {
            return ChatColor.GREEN;
        } else if (wins < 2001) {
            return ChatColor.DARK_RED;
        } else if (wins < 5001) {
            return ChatColor.YELLOW;
        } else if (wins < 10001) {
        return ChatColor.DARK_PURPLE;
        } else if (wins < 25001) {
            return ChatColor.AQUA;
        } else if (wins < 50001) {
            return ChatColor.LIGHT_PURPLE;
        } else {
            return ChatColor.YELLOW;
        }
    }

    public ChatColor getLossesColor(int losses) {
        if (losses < 50) {
            return ChatColor.DARK_PURPLE;
        } else if (losses < 75) {
            return ChatColor.LIGHT_PURPLE;
        } else if (losses < 100) {
            return ChatColor.DARK_RED;
        } else if (losses < 150) {
            return ChatColor.RED;
        } else if (losses < 200) {
            return ChatColor.DARK_GREEN;
        } else if (losses < 250) {
            return ChatColor.GOLD;
        } else if (losses < 300) {
            return ChatColor.WHITE;
        } else {
            return ChatColor.GRAY;
        }
    }

    public String getTitleColor(String title) {
        title = title.toUpperCase();
        if (title.contains("ROOKIE")) {
            return ChatColor.GRAY.toString();
        } else if (title.contains("IRON")) {
            return ChatColor.WHITE.toString();
        } else if (title.contains("GOLD")) {
            return ChatColor.GOLD.toString();
        } else if (title.contains("DIAMOND")) {
            return ChatColor.DARK_AQUA.toString();
        } else if (title.contains("MASTER")) {
            return ChatColor.DARK_GREEN.toString();
        } else if (title.contains("LEGEND")) {
            return ChatColor.DARK_RED + ChatColor.BOLD.toString();
        } else if (title.contains("GRANDMASTER")) {
            return ChatColor.YELLOW + ChatColor.BOLD.toString();
        } else if (title.contains("GODLIKE")) {
            return ChatColor.DARK_PURPLE + ChatColor.BOLD.toString();
        } else if (title.contains("WORLD ELITE")) {
            return ChatColor.AQUA.toString();
        } else if (title.contains("WORLD MASTER")) {
            return ChatColor.LIGHT_PURPLE.toString();
        } else if (title.contains("WORLD'S BEST")) {
            return ChatColor.GOLD.toString();
        } else {
            return ChatColor.GRAY.toString();
        }
    }

    public String getFormattedTitle(Duels duels) {
        String title = ((StatString)duels.title).getValue();
        String formattedTitle = title.replace("_", " ").replace("Cosmetictitle", "");

        if (this.isPrestigeTitle(title)) {
            DuelsModes duelMode = DuelsModes.valueOf(title.replace(" ", ""));
            String gamemodeName = duelMode.getName();
            String gamemodeJsonName = duelMode.getJsonName();

            /* calculate wins */
            int gamemodeWins = duels.duelJson.get(gamemodeJsonName + "_duel_wins").getAsInt();
            if (gamemodeWins < 100) {
                /* rookie I think? idfk */
            } else if (gamemodeWins < 250) {
                /* I think iron? */
            } else if (gamemodeWins < 500) {
                /* gold! */
            } else {
                /* world's best */
            }
        }

        return WordUtils.capitalize(formattedTitle.trim());
    }

    private boolean isPrestigeTitle(String title) {
        title = title.toUpperCase();
        return title.contains("ROOKIE") || title.contains("IRON") || title.contains("GOLD") || title.contains("DIAMOND") || title.contains("MASTER") || title.contains("LEGEND") || title.contains("GRANDMASTER") || title.contains("GODLIKE") || title.contains("WORLD ELITE") || title.contains("WORLD MASTER") || title.contains("WORLD'S BEST");
    }
}