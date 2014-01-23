package com.torstling.tdop.calculator;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class AdditionTokenType<S> implements TokenType<CalculatorNode, S> {
    private static final AdditionTokenType INSTANCE = new AdditionTokenType();

    @NotNull
    public Token<CalculatorNode, S> toToken(@NotNull LexingMatch match) {
        return new AdditionToken<S>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\+");
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static AdditionTokenType get() {
        return INSTANCE;
    }
}
