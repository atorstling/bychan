package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/28/14.
 */
public class ParsingPosition {
    @NotNull
    private final LexingMatch lexingMatch;

    public ParsingPosition(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }

    @Override
    public String toString() {
        return lexingMatch.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        if (!lexingMatch.equals(that.lexingMatch)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lexingMatch.hashCode();
    }
}
