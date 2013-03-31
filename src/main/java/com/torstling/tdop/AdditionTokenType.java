package com.torstling.tdop;

public class AdditionTokenType implements TokenType<CalculatorNode> {
    private static final AdditionTokenType INSTANCE = new AdditionTokenType();

    public Token<CalculatorNode> toToken(LexingMatch match) {
        return new AdditionToken(match);
    }

    public String getPattern() {
        return "\\+";
    }

    public static AdditionTokenType get() {
        return INSTANCE;
    }
}
