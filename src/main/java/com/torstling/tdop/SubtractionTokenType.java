package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class SubtractionTokenType implements TokenType<CalculatorNode> {
    private static final SubtractionTokenType INSTANCE = new SubtractionTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new SubtractionToken(match);
    }

    @NotNull
    public String getPattern() {
        return "-";
    }

    public static SubtractionTokenType get() {
        return INSTANCE;
    }
}
