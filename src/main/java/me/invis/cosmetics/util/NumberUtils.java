package me.invis.cosmetics.util;

public class NumberUtils {
    public static int random(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
