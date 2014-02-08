package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * A lexing match :)
 */
public class LexingMatch {

    private final int startPosition;
    private final int endPosition;
    @NotNull
    private final String text;

    public LexingMatch(int startPosition, int endPosition, @NotNull final String text) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.text = text;
    }

    @NotNull
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "LexingMatch{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", text='" + text + '\'' +
                '}';
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingMatch that = (LexingMatch) o;

        return endPosition == that.endPosition && startPosition == that.startPosition && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = startPosition;
        result = 31 * result + endPosition;
        result = 31 * result + text.hashCode();
        return result;
    }
}
