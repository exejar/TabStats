package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class TabStatsConfig extends Vigilant {
    // contact me Salmon, on discord or read https://github.com/Sk1erLLC/Vigilance/blob/master/src/main/kotlin/gg/essential/vigilance/example/ExampleConfig.kt for info on Vigilant.
    @Property(
            type = PropertyType.SWITCH,
            name = "Example Switch",
            description = "hi",
            category = "General"
    )
    private boolean exampleSwitch = true;

    // example getter so you don't static aboos
    public boolean getExampleswitch() {
        return exampleSwitch;
    }

    public TabStatsConfig() {
        super(new File("./config", References.MODID + ".toml"), References.MODNAME);
    }
}
