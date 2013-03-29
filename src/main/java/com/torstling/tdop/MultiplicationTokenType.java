package com.torstling.tdop;

public class MultiplicationTokenType implements TokenType<CalculatorNode> {
    public static final MultiplicationTokenType INSTANCE = new MultiplicationTokenType();

    public Token toToken(LexingMatch match) {
        return new MultiplicationToken();
    }

    public String getPattern() {
        return "\\*";
    }

    public static MultiplicationTokenType get() {
        return INSTANCE;
    }
}
