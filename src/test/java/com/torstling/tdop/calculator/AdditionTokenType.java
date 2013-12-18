package com.torstling.tdop.calculator;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
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

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static AdditionTokenType get() {
        return INSTANCE;
    }
}
