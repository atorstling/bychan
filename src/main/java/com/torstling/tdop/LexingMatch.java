package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

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
