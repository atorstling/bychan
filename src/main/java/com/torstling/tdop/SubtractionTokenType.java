package com.torstling.tdop;

public class SubtractionTokenType implements TokenType<CalculatorNode> {
    public static final SubtractionTokenType INSTANCE = new SubtractionTokenType();

    public Token toToken(LexingMatch match) {
        return new SubtractionToken();
    }

    public String getPattern() {
        return "-";
    }

    public static SubtractionTokenType get() {
        return INSTANCE;
    }
}
