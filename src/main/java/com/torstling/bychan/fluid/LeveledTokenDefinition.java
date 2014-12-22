package com.torstling.bychan.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

class LeveledTokenDefinition<N> {
    @NotNull
    private final TokenDefinition<N> definition;
    private final int levelCount;

    public LeveledTokenDefinition(@NotNull final TokenDefinition<N> definition, final int levelCount) {
        this.definition = definition;
        this.levelCount = levelCount;
    }

    @NotNull
    public Pattern getPattern() {
        return definition.getPattern();
    }

    @NotNull
    public String getTokenTypeName() {
        return definition.getTokenTypeName();
    }

    public int getLevel() {
        return levelCount;
    }

    @NotNull
    public String toString() {
        return levelCount + ":" + definition;
    }

    @Nullable
    public PrefixAstBuilder<N> getPrefixBuilder() {
        return definition.getPrefixBuilder();
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return definition;
    }

    @Nullable
    public InfixAstBuilder<N> getInfixBuilder() {
        return definition.getInfixBuilder();
    }
}
