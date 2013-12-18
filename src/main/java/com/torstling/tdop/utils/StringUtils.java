package com.torstling.tdop.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class StringUtils {
    @NotNull
    public static String join(@NotNull final Iterable<String> parts, @NotNull final String separator) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> i = parts.iterator(); i.hasNext(); ) {
            sb.append(i.next());
            if (i.hasNext()) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
