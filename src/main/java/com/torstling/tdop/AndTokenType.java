package com.torstling.tdop;

public class AndTokenType implements TokenType {

    public static final AndTokenType INSTANCE = new AndTokenType();

    public Token toToken(String value) {
        return new AndToken();
    }

    public String getPattern() {
        return "\\&";
    }

    public static AndTokenType get() {
        return INSTANCE;
    }
}
