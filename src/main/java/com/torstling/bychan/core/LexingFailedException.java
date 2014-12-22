package com.torstling.bychan.core;

import org.jetbrains.annotations.NotNull;

public class LexingFailedException extends RuntimeException {
    private final LexingPosition lexingPosition;

    public LexingFailedException(@NotNull final LexingPosition lexingPosition, @NotNull final String message) {
        super(message);
        this.lexingPosition = lexingPosition;
    }

    public LexingPosition getLexingPosition() {
        return lexingPosition;
    }
}
