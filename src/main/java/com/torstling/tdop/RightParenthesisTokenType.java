package com.torstling.tdop;

public class RightParenthesisTokenType<N extends Node> implements TokenType<N> {

    public Token<N> toToken(String value) {
        return new RightParenthesisToken<N>();
    }

    public String getPattern() {
        return "\\)";
    }

    public static <N extends Node> RightParenthesisTokenType<N> get() {
        return new RightParenthesisTokenType<N>();
    }
}
