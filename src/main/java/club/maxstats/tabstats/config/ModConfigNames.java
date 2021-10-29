package club.maxstats.tabstats.config;

public enum ModConfigNames {
    APIKEY("ApiKey"), VERSION("Version");

    private final String name;

    ModConfigNames(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
