package com.torstling.tdop.calculator;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
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
