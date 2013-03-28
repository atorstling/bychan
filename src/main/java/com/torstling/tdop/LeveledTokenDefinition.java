package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class LeveledTokenDefinition<N extends Node> {
    @NotNull
    private final TokenDefinition<N> definition;
    private final int levelCount;
    private int level;

    public LeveledTokenDefinition(@NotNull final TokenDefinition<N> definition, final int levelCount) {
        this.definition = definition;
        this.levelCount = levelCount;
    }

    public String getPattern() {
        return definition.getPattern();
    }

    public int getLevel() {
        return level;
    }
}
