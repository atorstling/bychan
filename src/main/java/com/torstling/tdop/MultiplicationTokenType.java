package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class MultiplicationTokenType implements TokenType<CalculatorNode> {
    private static final MultiplicationTokenType INSTANCE = new MultiplicationTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new MultiplicationToken(match);
    }

    @NotNull
    public String getPattern() {
        return "\\*";
    }

    public static MultiplicationTokenType get() {
        return INSTANCE;
    }
}
