package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/28/14.
 */
public class ParsingPosition {
    private final int position;
    @NotNull
    private final String remainingText;

    public ParsingPosition(int position, @NotNull String remainingText) {
        this.position = position;
        this.remainingText = remainingText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        if (position != that.position) return false;
        if (!remainingText.equals(that.remainingText)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + remainingText.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ParsingPosition{" +
                "position=" + position +
                ", remainingText='" + remainingText + '\'' +
                '}';
    }
}
