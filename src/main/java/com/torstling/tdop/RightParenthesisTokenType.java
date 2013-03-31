package com.torstling.tdop;

public class RightParenthesisTokenType<N extends Node> implements TokenType<N> {

    public static final RightParenthesisTokenType INSTANCE = new RightParenthesisTokenType<>();

    public Token<N> toToken(LexingMatch match) {
        return new RightParenthesisToken<N>(match);
    }

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
