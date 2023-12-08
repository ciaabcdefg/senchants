package com.ciaabcdefg.senchants.util;

public class Essentials {
    public static double Clamp(double value, double min, double max) {
        return Math.min(max, Math.max(value, min));
    }

    public static double Remap(double value, double oldMin, double oldMax, double newMin, double newMax) {
        double slope = (newMax - newMin) / (oldMax - oldMin);
        return newMin + slope * (value - oldMin);
    }


}
