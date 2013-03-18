package com.torstling.tdop;

public class RightParenthesisTokenType implements TokenType {

    public static final RightParenthesisTokenType INSTANCE = new RightParenthesisTokenType();

    public Token toToken(String value) {
        return new RightParenthesisToken();
    }

    public String getPattern() {
        return "\\)";
    }

    public static RightParenthesisTokenType get() {
        return INSTANCE;
    }
}
