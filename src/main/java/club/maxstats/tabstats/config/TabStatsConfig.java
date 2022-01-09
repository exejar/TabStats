package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.awt.*;
import java.io.File;

public class TabStatsConfig extends Vigilant {

    @Property(
            type = PropertyType.TEXT,
            name = "API Key",
            description = "Hypixel API key to be used when fetching stats",
            category = "General",
            protectedText = true
    )
    private String apiKey = "";

    @Property(
            type = PropertyType.SWITCH, name = "TabStats Toggle",
            description = "Enables or Disables TabStats",
            category = "General"
    )
    private boolean toggleMod = true;

    @Property(
            type = PropertyType.SWITCH, name = "Text Shadow Toggle",
            description = "When enabled, text in tab will render with text shadow.",
            category = "Customization"
    )
    private boolean textShadow = true;

    @Property(
            type = PropertyType.SLIDER,
            name = "Tab Scale",
            description = "(4 is 1x scale) Use this slider to adjust the scale of tab. \n§eNumbers will round to the nearest even number.",
            category = "Customization",
            min = 1,
            max = 12
    )
    private int tabScale = 4;

    @Property(
            type = PropertyType.SLIDER,
            name = "Tab Opacity",
            description = "Opacity Slider",
            category = "Customization",
            min = 0,
            max = 255
    )
    private int tabOpacity = 50;

    @Property(
            type = PropertyType.COLOR,
            name = "Outer Tab Color",
            description = "Adjust the color and opacity of the outer tab.",
            category = "Customization",
            allowAlpha = false
    )
    private Color outerTabbgColor = new Color(0,0,0,50);

    @Property(
            type = PropertyType.COLOR,
            name = "Inner Tab Color",
            description = "Adjust the color and opacity of the inner tab. \n§eWhen opacity is set below the outer tabs, the value will be the same as that of the outer tab.",
            category = "Customization",
            allowAlpha = false
    )
    private Color innerTabBgColor = new Color(0,0,0,50);

    public String getApiKey() { return this.apiKey; }

    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public boolean isModToggled() { return this.toggleMod; }

    public boolean getTextShadow() {
        return textShadow;
    }

    public int getTabScale() { return this.tabScale; }

    public int getTabOpacity() { return this.tabOpacity; }

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
