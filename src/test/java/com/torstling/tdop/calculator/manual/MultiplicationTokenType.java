package com.torstling.tdop.calculator.manual;

import com.torstling.tdop.calculator.nodes.CalculatorNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class MultiplicationTokenType<S> implements TokenType<CalculatorNode> {
    private static final MultiplicationTokenType INSTANCE = new MultiplicationTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new MultiplicationToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\*");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> MultiplicationTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}