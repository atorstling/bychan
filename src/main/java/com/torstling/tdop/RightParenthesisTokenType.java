package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class RightParenthesisTokenType<N extends Node> implements TokenType<N> {

    private static final RightParenthesisTokenType INSTANCE = new RightParenthesisTokenType<>();

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new RightParenthesisToken<N>(match);
    }

    @NotNull
    public String getPattern() {
        return "\\)";
    }

    public static <N extends Node> RightParenthesisTokenType<N> get() {
        //noinspection unchecked
        return (RightParenthesisTokenType<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
