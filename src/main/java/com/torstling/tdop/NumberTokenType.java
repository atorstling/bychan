package com.torstling.tdop;

public class NumberTokenType implements TokenType<CalculatorNode> {
    public static final NumberTokenType INSTANCE = new NumberTokenType();

    public Token toToken(LexingMatch match) {
        return NumberToken.valueOf(match);
    }

    public String getPattern() {
        return "\\d+";
    }

    public static NumberTokenType get() {
        return INSTANCE;
    }
}
