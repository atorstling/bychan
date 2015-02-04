package org.bychan.core;

import org.jetbrains.annotations.NotNull;

/**
 * Tries to match input against an exact string
 */
public class StringMatcher implements TokenMatcher {
    @NotNull
    private final String text;

    public StringMatcher(@NotNull final String text) {
        this.text = text;
    }

    @Override
    public int tryMatch(@NotNull String input, int searchStart) {
        if (input.regionMatches(searchStart, text, 0, text.length())) {
            return searchStart + text.length();
        }
        return -1;
    }
}
