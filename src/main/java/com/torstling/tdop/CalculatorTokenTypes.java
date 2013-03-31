package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

class CalculatorTokenTypes {
    @NotNull
    public static List<TokenType<CalculatorNode>> get() {
        return Arrays.asList(
                NumberTokenType.get(),
                SubtractionTokenType.get(),
                AdditionTokenType.get(),
                MultiplicationTokenType.get(),
                LeftParenthesisTokenType.<CalculatorNode>get(),
                RightParenthesisTokenType.<CalculatorNode>get());
    }
}
