package com.torstling.tdop;

public class LeftParenthesisTokenType<N extends Node> implements TokenType<N> {

    public Token<N> toToken(String value) {
        return new LeftParenthesisToken<N>();
    }

    public String getPattern() {
        return "\\(";
    }

    public static <N extends Node> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<N>();
    }
}
