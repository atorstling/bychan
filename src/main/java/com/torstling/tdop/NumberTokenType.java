package com.torstling.tdop;

public class NumberTokenType implements TokenType<CalculatorNode> {
    public static final NumberTokenType INSTANCE = new NumberTokenType();

    public Token toToken(String value) {
        return NumberToken.valueOf(value);
    }

    public String getPattern() {
        return "\\d+";
    }

    public static NumberTokenType get() {
        return INSTANCE;
    }
}
