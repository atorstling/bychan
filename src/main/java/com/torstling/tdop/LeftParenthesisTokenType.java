package com.torstling.tdop;

public class LeftParenthesisTokenType implements TokenType {

    public static final LeftParenthesisTokenType INSTANCE = new LeftParenthesisTokenType();

    public Token toToken(String value) {
        return new LeftParenthesisToken();
    }

    public String getPattern() {
        return "\\(";
    }

    public static LeftParenthesisTokenType get() {
        return INSTANCE;
    }
}
