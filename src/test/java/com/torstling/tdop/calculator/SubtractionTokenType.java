package com.torstling.tdop.calculator;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SubtractionTokenType implements TokenType<CalculatorNode> {
    private static final SubtractionTokenType INSTANCE = new SubtractionTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new SubtractionToken(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("-");
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static SubtractionTokenType get() {
        return INSTANCE;
    }
}
