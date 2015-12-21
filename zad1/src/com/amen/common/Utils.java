package com.amen.common;

/**
 * Created by aborowski
 */
public class Utils {
    public static <T extends Comparable<T>> T max(T... elements) {
        T max = elements[0];

        for (T el : elements) {
            if (max.compareTo(el) < 0) {
                max = el;
            }
        }
        return max;
    }
}
