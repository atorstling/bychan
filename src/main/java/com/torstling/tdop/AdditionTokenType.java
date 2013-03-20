package com.torstling.tdop;

public class AdditionTokenType implements TokenType<CalculatorNode> {
    public static final AdditionTokenType INSTANCE = new AdditionTokenType();

    public Token toToken(String value) {
        return new AdditionToken();
    }

    public String getPattern() {
        return "\\+";
    }

    public static AdditionTokenType get() {
        return INSTANCE;
    }
}
