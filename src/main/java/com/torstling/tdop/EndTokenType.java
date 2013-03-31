package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class EndTokenType<N extends Node> implements TokenType<N> {

    public static final EndTokenType INSTANCE = new EndTokenType<>();

    @Override
    public Token<N> toToken(@NotNull LexingMatch match) {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @Override
    public String getPattern() {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @NotNull
    public static <N extends Node> EndTokenType<N> get() {
        //noinspection unchecked
        return (EndTokenType<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
