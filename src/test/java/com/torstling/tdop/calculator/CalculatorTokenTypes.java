package com.torstling.tdop.calculator;

import com.torstling.tdop.core.TokenType;
import com.torstling.tdop.generic.LeftParenthesisTokenType;
import com.torstling.tdop.generic.RightParenthesisTokenType;
import com.torstling.tdop.generic.WhitespaceTokenType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CalculatorTokenTypes {
    @NotNull
    public static <S> List<TokenType<CalculatorNode, S>> get() {
        return Arrays.<TokenType<CalculatorNode, S>>asList(
                new WhitespaceTokenType<CalculatorNode, S>(),
                NumberTokenType.get(),
                SubtractionTokenType.get(),
                AdditionTokenType.get(),
                MultiplicationTokenType.get(),
                LeftParenthesisTokenType.<CalculatorNode, S>get(),
                RightParenthesisTokenType.<CalculatorNode, S>get());
    }
}
