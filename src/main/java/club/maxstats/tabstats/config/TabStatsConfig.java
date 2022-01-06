package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.awt.*;
import java.io.File;

public class TabStatsConfig extends Vigilant {

    @Property(
            type = PropertyType.COLOR,
            name = "Background Color and Opacity of the Outer Tab",
            description = "Adjust the background color and opacity of the outer tab layer",
            category = "General"
    )
    private Color outerTabbgColor = new Color(0,0,0,50);

    @Property(
            type = PropertyType.COLOR,
            name = "Background Color and Opacity of the inner Tab",
            description = "Adjust the background color and opacity of the inner tab layer",
            category = "General"
    )
    private Color innerTabbgColor = new Color(0,0,0,50);

    public Color getOuterTabBgColor() {
        return outerTabbgColor;
    }

    public Color getInnerTabBgColor() {
        return innerTabbgColor;
    }


    public TabStatsConfig() {
        super(new File("./config", References.MODID + ".toml"), References.MODNAME);
    }
}
