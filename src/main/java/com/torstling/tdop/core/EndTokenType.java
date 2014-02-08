package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class EndTokenType<N, S> implements TokenType<N, S> {

    private static final EndTokenType INSTANCE = new EndTokenType<>();

    @NotNull
    @Override
    public Token<N, S> toToken(@NotNull LexingMatch match) {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @Override
    public boolean include() {
        return true;
    }

    @NotNull
    public static <N, S> EndTokenType<N, S> get() {
        //noinspection unchecked
        return (EndTokenType<N, S>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
