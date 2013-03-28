package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class TokenDefinition<N extends Node> {
    @NotNull
    private final String pattern;
    @NotNull
    private final PrefixAstBuilder<N> prefixBuilder;
    @NotNull
    private final InfixAstBuilder<N> infixBuilder;
    @NotNull
    private final boolean filterOutBeforeParsing;

    public TokenDefinition(@NotNull final String pattern, @NotNull final PrefixAstBuilder<N> prefixBuilder, @NotNull final InfixAstBuilder<N> infixBuilder, boolean filterOutBeforeParsing) {

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
}
