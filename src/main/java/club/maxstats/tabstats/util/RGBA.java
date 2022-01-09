package club.maxstats.tabstats.util;

import java.awt.*;

public class RGBA {
    private int red;
    private int green;
    private int blue;
    private int opacity;
    private int value;

    public RGBA(int red, int green, int blue, int opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        this.value = ((opacity & 0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8)  |
                ((blue & 0xFF) << 0);
    }

    public RGBA(int color) {
        this.value = color;
        this.red = getRed(color);
        this.green = getGreen(color);
        this.blue = getBlue(color);
        this.opacity = getOpacity(color);
    }

    public int getRed() { return this.red; }

    public int getGreen() { return this.green; }

    public int getBlue() { return this.blue; }

    public int getOpacity() { return this.opacity; }

    public RGBA setRed(int red) {
        this.red = red;
        return this;
    }

    public RGBA setGreen(int green) {
        this.green = green;
        return this;
    }

    public RGBA setBlue(int blue) {
        this.blue = blue;
        return this;
    }

    public RGBA setOpacity(int opacity) {
        this.opacity = opacity;
        return this;
    }

    public int toRGB() {
        return this.value;
    }

    public RGBA getNew() {
        return new RGBA(this.red, this.green, this.blue, this.opacity);
    }

    public static RGBA adjustOpacity(int color, int opacity) {
        return new RGBA(getRed(color), getGreen(color), getBlue(color), opacity);
    }

    public static int getRed(int color) { return color >> 16 & 0xFF; }

    public static int getGreen(int color) { return color >> 8 & 0xFF; }

    public static int getBlue(int color) { return color & 0xFF; }

    public static int getOpacity(int color) { return color >> 24 & 0xFF; }
}
