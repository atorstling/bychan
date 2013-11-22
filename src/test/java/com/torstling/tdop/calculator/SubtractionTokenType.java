package com.torstling.tdop.calculator;

import com.torstling.tdop.LexingMatch;
import com.torstling.tdop.Token;
import com.torstling.tdop.TokenType;
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
