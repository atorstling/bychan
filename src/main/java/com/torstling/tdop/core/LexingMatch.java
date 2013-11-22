package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * A lexing match :)
 */
public class LexingMatch {

    @NotNull
    private final String text;

    public LexingMatch(@NotNull final String text) {
        this.text = text;
    }

    @NotNull
    public String getText() {
        return text;
    }
}
