package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class LeftParenthesisTokenType<N extends Node> implements TokenType<N> {

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new LeftParenthesisToken<N>(match);
    }

    @NotNull
    public String getPattern() {
        return "\\(";
    }

    public static <N extends Node> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<N>();
    }
}
