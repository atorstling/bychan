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
}
