package club.maxstats.tabstats.playerapi.api.games.duels;

public enum DuelsModes {
    UHC("UHC", "uhc"),
    SUMO("Sumo", "sumo"),
    NODEBUFF("NoDebuff", "potion");

    String name, jsonName;
    DuelsModes(String name, String jsonName) {
        this.name = name;
        this.jsonName = jsonName;
    }

    public String getName() { return this.name; }

    public String getJsonName() { return this.jsonName; }
}
