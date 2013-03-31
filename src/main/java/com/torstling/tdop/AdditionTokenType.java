package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class AdditionTokenType implements TokenType<CalculatorNode> {
    private static final AdditionTokenType INSTANCE = new AdditionTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new AdditionToken(match);
    }

    @NotNull
    public String getPattern() {
        return "\\+";
    }

    public static AdditionTokenType get() {
        return INSTANCE;
    }
}
