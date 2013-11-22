package com.torstling.tdop.calculator;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
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
