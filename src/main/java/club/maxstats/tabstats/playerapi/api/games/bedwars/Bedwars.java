package club.maxstats.tabstats.playerapi.api.games.bedwars;

import club.maxstats.tabstats.playerapi.api.games.HypixelGames;
import club.maxstats.tabstats.playerapi.api.stats.Stat;
import club.maxstats.tabstats.playerapi.api.stats.StatInt;
import club.maxstats.tabstats.playerapi.api.stats.StatString;
import club.maxstats.tabstats.playerapi.exception.GameNullException;
import club.maxstats.tabstats.util.ChatColor;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Bedwars extends BedwarsUtil {
    private JsonObject bedwarsJson, wholeObject;
    private List<Stat> statList;
    private List<Stat> formattedStatList;
    public Stat gamesPlayed, finalKills, finalDeaths, wins, losses, kills, deaths, bedsBroken, bedsLost, winstreak, star;

    public Bedwars(String playerName, String playerUUID, JsonObject wholeObject) {
        super(playerName, playerUUID);
        this.wholeObject = wholeObject;
        this.achievementObj = wholeObject.get("player").getAsJsonObject().get("achievements").getAsJsonObject();
        this.playerObject = wholeObject.get("player").getAsJsonObject();
        this.statList = new ArrayList<>();
        this.formattedStatList = new ArrayList<>();

        if (setData(HypixelGames.BEDWARS)) {
            this.statList = setStats(
                    // "bedwars_level" is the Api name of the star.
                    // If you wish to add any other stats, you add them like this, statName is whatever name you want to call it, then jsonName is the name of the stat in the API
                    // and the 3rd parameter is the json object in which this api stat resides. So winstreak is inside of the bedwars json object along with all the other bedwars
                    // statistics. For some reason, bedwars level is inside of your achievements?? idk why
                    this.star = new StatInt("Level", "bedwars_level", this.achievementObj),
                    this.winstreak = new StatInt("Winstreak", "winstreak", this.bedwarsJson),
                    this.gamesPlayed = new StatInt("Games Played", "games_played_bedwars", this.bedwarsJson),
                    this.finalKills = new StatInt("Final Kills", "final_kills_bedwars", this.bedwarsJson),
                    this.finalDeaths = new StatInt("Final Deaths", "final_deaths_bedwars", this.bedwarsJson),
                    this.wins = new StatInt("Wins", "wins_bedwars", this.bedwarsJson),
                    this.losses = new StatInt("Losses", "losses_bedwars", this.bedwarsJson),
                    this.kills = new StatInt("Kills", "kills_bedwars", this.bedwarsJson),
                    this.deaths = new StatInt("Deaths", "deaths_bedwars", this.bedwarsJson),
                    this.bedsBroken = new StatInt("Beds Broken", "beds_broken_bedwars", this.bedwarsJson),
                    this.bedsLost = new StatInt("Beds Lost", "beds_lost_bedwars", this.bedwarsJson));
        } else {
            this.formattedStatList.add(new StatString("Star", ChatColor.RED + "NICKED"));
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
                this.bedwarsJson = obj;
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
        return String.format("%s%s", getFkdrColor(getFkdr(this)), getFkdr(this));
    }

    @Override
    public HypixelGames getGame() {
        return HypixelGames.BEDWARS;
    }

    @Override
    public List<Stat> getStatList() {
        return this.statList;
    }

    @Override
    public List<Stat> getFormattedStatList() {
        List<Stat> returnList = new ArrayList<>(this.formattedStatList);
        StatString session = new StatString("Session");
        session.setValue(this.getFormattedSessionTime());
        returnList.add(session);
        return returnList;
    }

    @Override
    public void setFormattedStatList() {
        StatString star = new StatString("Star");
        star.setValue(this.getStarColor(((StatInt)this.star).getValue()).toString() + ((StatInt)this.star).getValue());
        this.formattedStatList.add(star);

        StatString ws = new StatString("WS");
        ws.setValue(this.getWSColor(((StatInt)winstreak).getValue()).toString() + ((StatInt)winstreak).getValue());
        this.formattedStatList.add(ws);

        StatString fkdr = new StatString("FKDR");
        fkdr.setValue(this.getFkdrColor(this.getFkdr(this)).toString() + this.getFkdr(this));
        this.formattedStatList.add(fkdr);

        StatString wlr = new StatString("WLR");
        wlr.setValue(this.getWlrColor(this.getWlr(this)).toString() + this.getWlr(this));
        this.formattedStatList.add(wlr);

        StatString bblr = new StatString("BBLR");
        bblr.setValue(this.getBblrColor(this.getBblr(this)).toString() + this.getBblr(this));
        this.formattedStatList.add(bblr);
    }
}
