package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class WrappedDefinition<N extends Node> implements TokenType<N>, Token<N>{
    @NotNull
    private final LeveledTokenDefinition<N> def;

    public WrappedDefinition(@NotNull final LeveledTokenDefinition<N> def) {
        this.def = def;
    }

    @Override
    public Token<N> toToken(@NotNull final String value) {
        return this;
    }

    @Override
    public String getPattern() {
        return def.getPattern();
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public N infixParse(@NotNull N left, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int infixBindingPower() {
        return def.getLevel();
    }

    public String toString() {
        return "" + def;
    }
}
