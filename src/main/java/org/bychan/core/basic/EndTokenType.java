package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class EndTokenType<N> implements TokenType<N> {

    private static final EndTokenType INSTANCE = new EndTokenType<>();

    @NotNull
    @Override
    public Token<N> toToken(@NotNull LexingMatch match) {
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
    public static <N> EndTokenType<N> get() {
        //noinspection unchecked
        return (EndTokenType<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}