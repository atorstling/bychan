package com.torstling.bychan.calculator.manual;

import com.torstling.bychan.calculator.nodes.CalculatorNode;
import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SubtractionTokenType<S> implements TokenType<CalculatorNode> {
    private static final SubtractionTokenType INSTANCE = new SubtractionTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new SubtractionToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("-");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> SubtractionTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
