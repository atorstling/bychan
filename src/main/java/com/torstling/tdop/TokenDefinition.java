package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TokenDefinition<N extends Node> {
    @NotNull
    private final String pattern;
    @Nullable
    private final PrefixAstBuilder<N> prefixBuilder;
    @Nullable
    private final InfixAstBuilder<N> infixBuilder;
    private final boolean filterOutBeforeParsing;

    public TokenDefinition(@NotNull final String pattern, @Nullable final PrefixAstBuilder<N> prefixBuilder, @Nullable final InfixAstBuilder<N> infixBuilder, boolean filterOutBeforeParsing) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
        this.filterOutBeforeParsing = filterOutBeforeParsing;
    }

    @NotNull
    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }

    @Nullable
    public PrefixAstBuilder<N> getPrefixBuilder() {
        return prefixBuilder;
    }

    @Nullable
    public InfixAstBuilder<N> getInfixBuilder() {
        return infixBuilder;
    }
}
