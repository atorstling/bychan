package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
public class LexingFailedInformation {
    @NotNull
    private final String message;
    @NotNull
    private final LexingPosition lexingPosition;

    public LexingFailedInformation(@NotNull final String message, @NotNull final LexingPosition lexingPosition) {
        this.message = message;
        this.lexingPosition = lexingPosition;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    @NotNull
    public LexingPosition getLexingPosition() {
        return lexingPosition;
    }
}
