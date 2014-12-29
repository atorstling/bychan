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
    public static TextPosition getTextPosition(String string, int index) {
        BufferedReader reader = new BufferedReader(new StringReader(string));
        return getTextPosition(reader, index);
    }

    private static TextPosition getTextPosition(@NotNull BufferedReader reader, int index) {
        try {
            int current;
            int row = 1, col = 1;
            //Only proceed to directly _before_ the character in question. This
            //is done since the newline character itself is considered to belong to the
            //row before. Therefore we don't want to increase the counter until we _pass_
            //the newline char.
            for (int read=0; read < index; read++) {
                current = reader.read();
                if (current == -1) {
                    throw new StringIndexOutOfBoundsException(index);
                }
                if (current=='\n') {
                    row++;
                    col=1;
                } else {
                    col++;
                }
            }
            //Check that the character in question actually exists
            if (reader.read() == -1) {
                throw new StringIndexOutOfBoundsException(index);
            }
            return new TextPosition(row, col);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
