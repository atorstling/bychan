package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

public class LexingFailedException extends RuntimeException {
    private final int lexingPosition;
    @NotNull
    private final String matchSection;

    public LexingFailedException(int lexingPosition, @NotNull String matchSection) {
        super("No matching rule for char-range starting at " + lexingPosition + ": '" + matchSection + "'");
        this.lexingPosition = lexingPosition;
        this.matchSection = matchSection;
    }

    public int getStreamPosition() {
        return lexingPosition;
    }

    @NotNull
    public String getMatchSection() {
        return matchSection;
    }
}
