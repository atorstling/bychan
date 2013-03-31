package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class LexingMatch {

    @NotNull
    private final String text;

    public LexingMatch(@NotNull final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
