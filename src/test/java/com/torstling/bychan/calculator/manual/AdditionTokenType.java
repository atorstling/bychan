package com.torstling.bychan.calculator.manual;

import com.torstling.bychan.calculator.nodes.CalculatorNode;
import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class AdditionTokenType<S> implements TokenType<CalculatorNode> {
    private static final AdditionTokenType INSTANCE = new AdditionTokenType();

    @NotNull
    public Token<CalculatorNode> toToken(@NotNull LexingMatch match) {
        return new AdditionToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\+");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <S> AdditionTokenType<S> get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
