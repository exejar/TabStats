package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.Handler;
import club.maxstats.tabstats.util.References;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;

import static club.maxstats.tabstats.config.ModConfigNames.APIKEY;

public class ModConfig {
    private String apiKey;
    private static ModConfig instance;
    private static final JsonParser parser = new JsonParser();

    public static ModConfig getInstance() {
        if (instance == null) instance = new ModConfig();
        return instance;

    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String key) {
        apiKey = key;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void makeFile() {
        try {
            if (!getFile().exists()) {
                getFile().getParentFile().mkdirs();
                getFile().createNewFile();
                try (FileWriter writer = new FileWriter(getFile())) {
                    writer.write("{}");
                    writer.flush();
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfigFromFile() {
        if (!getFile().exists()) makeFile();
        apiKey = getString(APIKEY);
    }

    public File getFile() {
        String file = References.LAUNCHER_DIRECTORY + File.separator + "apikey.json";
        return new File(file);
    }

    public void init() {
        loadConfigFromFile();
    }

    public void save() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(APIKEY.toString(), getApiKey());
        try (Writer writer = new FileWriter(getFile())) {
            Handler.getGson().toJson(map, writer);
        } catch (Exception ex) {
            System.out.println("Unable to save config file");
            ex.printStackTrace();
        }
    }

    public String getString(ModConfigNames key) {
        String s = "";
        try {
            JsonObject object = parser.parse(new FileReader(getFile())).getAsJsonObject();
            s = object.get(key.toString()).getAsString();
        } catch (NullPointerException ignored) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }
}
