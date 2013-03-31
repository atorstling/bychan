package com.torstling.tdop;

public class LeftParenthesisTokenType<N extends Node> implements TokenType<N> {

    public Token<N> toToken(LexingMatch match) {
        return new LeftParenthesisToken<N>(match);
    }

    public String getPattern() {
        return "\\(";
    }

    public static <N extends Node> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<N>();
    }
}
