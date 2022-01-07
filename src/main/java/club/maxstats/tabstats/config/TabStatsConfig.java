package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.awt.*;
import java.io.File;

public class TabStatsConfig extends Vigilant {

    @Property(
            type = PropertyType.SWITCH, name = "Entirely Toggle TabStats",
            description = "Entirely toggles TabStats on and off.",
            category = "General"
    )
    public static boolean toggleMod = true;

    @Property(
            type = PropertyType.SWITCH, name = "Toggle Text Shadow",
            description = "When enabled text in tab will render with text shadow.",
            category = "Customization"
    )
    private boolean textShadow = true;
/*
    @Property(
            type = PropertyType.SLIDER,
            name = "Tab Scale",
            description = "Use this slider to adjust the scale of tab. \n§eNumbers will round to the nearest even number.",
            category = "Customization",
            min = 0,
            max = 20
    )
    private int scale = 12;
*/
    @Property(
            type = PropertyType.COLOR,
            name = "Outer Tab Color",
            description = "Adjust the color and opacity of the outer tab.",
            category = "Customization"
    )
    private Color outerTabbgColor = new Color(0,0,0,50);

    @Property(
            type = PropertyType.COLOR,
            name = "Inner Tab Color",
            description = "Adjust the color and opacity of the inner tab. \n§eWhen opacity is set below the outer tabs, the value will be the same as that of the outer tab.",
            category = "Customization"
    )
    private Color innerTabBgColor = new Color(0,0,0,50);
/*
    public int getScale() {
        if (scale % 2 ==1)
            return scale++;
        else
            return scale;
    }
*/
    public boolean getTextShadow() {
        return textShadow;
    }

    public Color getOuterTabBgColor() {
        return outerTabbgColor;
    }

    public Color getInnerTabBgColor() {
        return innerTabBgColor;
    }

    public TabStatsConfig() {
        super(new File("./config", References.MODID + ".toml"), References.MODNAME);
    }

}
