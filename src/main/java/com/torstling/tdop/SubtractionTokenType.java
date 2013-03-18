package com.torstling.tdop;

public class SubtractionTokenType implements TokenType {
    public static final SubtractionTokenType INSTANCE = new SubtractionTokenType();

    public Token toToken(String value) {
        return new SubtractionToken();
    }

    public String getPattern() {
        return "-";
    }

    public static SubtractionTokenType get() {
        return INSTANCE;
    }
}
