package com.torstling.tdop.calculator.manual;

import com.torstling.tdop.calculator.nodes.CalculatorNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class NumberTokenType<S> implements TokenType<CalculatorNode> {
    private static final NumberTokenType INSTANCE = new NumberTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return NumberToken.valueOf(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\d+");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> NumberTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
