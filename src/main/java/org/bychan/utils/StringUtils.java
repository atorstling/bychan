package org.bychan.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class StringUtils {

    private StringUtils() {}

    @NotNull
    public static String repeat(@NotNull String s, int times) {
        StringBuilder sb = new StringBuilder(s.length() * times);
        for (int i=0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
