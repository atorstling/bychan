package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class NumberTokenType implements TokenType<CalculatorNode> {
    private static final NumberTokenType INSTANCE = new NumberTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return NumberToken.valueOf(match);
    }

    @NotNull
    public String getPattern() {
        return "\\d+";
    }

    public static NumberTokenType get() {
        return INSTANCE;
    }
}
