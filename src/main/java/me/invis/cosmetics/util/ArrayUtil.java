package me.invis.cosmetics.util;

public class ArrayUtil {
    public static <A> A random(A[] array) {
        return array[NumberUtils.random(0, array.length - 1)];
    }
}
