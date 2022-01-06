package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.awt.*;
import java.io.File;

public class TabStatsConfig extends Vigilant {

    @Property(
            type = PropertyType.SWITCH,
            name = "Toggle Text Shadow",
            description = "When enabled text in tab will render with text shadow.",
            category = "General"
    )
    private boolean textShadow = true;
    
    // property below isn't in use until the header and footer problem has been fixed.
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

    public boolean getTextShadow() {
        return textShadow;
    }

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
