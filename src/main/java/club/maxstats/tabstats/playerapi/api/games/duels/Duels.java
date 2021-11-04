package club.maxstats.tabstats.playerapi.api.games.duels;

import club.maxstats.tabstats.playerapi.api.games.HypixelGames;
import club.maxstats.tabstats.playerapi.api.stats.Stat;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.playerapi.api.stats.StatString;
import club.maxstats.tabstats.playerapi.exception.GameNullException;
import club.maxstats.tabstats.util.ChatColor;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Duels extends DuelsUtil {
    private JsonObject DuelsJson;
    private final JsonObject wholeObject;
    private List<Stat> statList;
    private final List<Stat> formattedStatList;
    public Stat winstreak, bestWinstreak, wins, losses, kills;

    public Duels(String playerName, String playerUUID, JsonObject wholeObject) {
        super(playerName, playerUUID);
        this.wholeObject = wholeObject;
        this.playerObject = wholeObject.get("player").getAsJsonObject();
        this.statList = new ArrayList<>();
        this.formattedStatList = new ArrayList<>();

        if (setData(HypixelGames.DUELS)) {
            this.statList = setStats(
                    this.winstreak = new StatInt("Winstreak", "current_winstreak", this.DuelsJson),
                    this.bestWinstreak = new StatInt("Best Winstreak", "best_overall_winstreak", this.DuelsJson),
                    this.wins = new StatInt("Wins", "wins", this.DuelsJson),
                    this.losses = new StatInt("Losses", "losses", this.DuelsJson),
                    this.kills = new StatInt("Kills", "kills", this.DuelsJson));
        } else {
            this.formattedStatList.add(new StatString("Wins", ChatColor.RED + "NICKED"));
        }
    }

    @Override
    public boolean setData(HypixelGames game) {
        this.isNicked = false;
        this.hasPlayed = false;

        try {
            JsonObject obj = getGameData(wholeObject, game);
            if (!this.isNicked) {
                this.hasPlayed = true;
                this.DuelsJson = obj;
                return true;
            }
            return false;
        } catch (GameNullException ex) {
            if (!this.isNicked) {
                System.out.println(String.format("Maybe %s has never played %s before", getPlayerName(), game.getGameName()));
            }

            System.out.println("Failed to Set Data");
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String getFormattedStats() {
        return String.format("%s%s", getWlrColor(getWlr(this)), getWlr(this));
    }

    @Override
    public HypixelGames getGame() {
        return HypixelGames.DUELS;
    }

    @Override
    public List<Stat> getStatList() {
        return this.statList;
    }


    /* retrieves the formatted stat list */
    @Override
    public List<Stat> getFormattedStatList() {
        return this.formattedStatList;
    }

    /* sets the formatted stat list when the player is first grabbed */
    /* only set a single time */
    @Override
    public void setFormattedStatList() {
        StatString ws = new StatString("WS");
        ws.setValue(this.getWSColor(((StatInt)this.winstreak).getValue()).toString() + ((StatInt)this.winstreak).getValue());
        this.formattedStatList.add(ws);

        StatString bws = new StatString("BWS");
        bws.setValue(this.getBestWSColor(((StatInt)this.bestWinstreak).getValue()).toString() + ((StatInt)this.bestWinstreak).getValue());
        this.formattedStatList.add(bws);

        StatString ks = new StatString("KILLS");
        ks.setValue(this.getKillsColor(((StatInt)this.kills).getValue()).toString() + ((StatInt)this.kills).getValue());
        this.formattedStatList.add(ks);

        StatString WLR = new StatString("WLR");
        WLR.setValue(this.getWlrColor(this.getWlr(this)).toString() + this.getWlr(this));
        this.formattedStatList.add(WLR);

        StatString wins = new StatString("WINS");
        wins.setValue(/* this sets the color >>*/ this.getWinsColor(((StatInt)this.wins).getValue()).toString() + /* this is what's actually displayed >>>*/ ((StatInt)this.wins).getValue());
        this.formattedStatList.add(wins);

        StatString losses = new StatString("LOSSES");
        losses.setValue(this.getLossesColor(((StatInt)this.losses).getValue()).toString() + ((StatInt)this.losses).getValue());
        this.formattedStatList.add(losses);
    }
}