package com.majorpotato.steampowered.util;


public class MiscUtil {

    public static int rgbToInt(float r, float g, float b) {
        return ((int)(r*255) << 16) + ((int)(g*255) << 8) + ((int)(b*255));
    }

    public static int rgbaToInt(float r, float g, float b, float a) {
        return ((int)(r*255) << 24) + ((int)(g*255) << 16) + ((int)(b*255) << 8) + ((int)(a*255));
    }
}
