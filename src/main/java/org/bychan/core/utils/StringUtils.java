package org.bychan.core.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

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

    @NotNull
    public static TextPosition getTextPosition(@NotNull String string, int index) {
        BufferedReader reader = new BufferedReader(new StringReader(string));
        return getTextPosition(reader, index);
    }

    @NotNull
    private static TextPosition getTextPosition(@NotNull BufferedReader reader, int index) {
        try {
            //Bother with previous token since we don't want to increase row until _after_ a newline char.
            int current=-1, previous;
            int row = 1, col = 1;
            for (int read=0; read <= index; read++) {
                previous = current;
                current = reader.read();
                if (current == -1) {
                    throw new IndexOutOfBoundsException("At index " + index);
                }
                if (previous=='\n') {
                    row++;
                    col=1;
                } else if (previous != -1) {
                    col++;
                }
            }
            return new TextPosition(index, row, col);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
