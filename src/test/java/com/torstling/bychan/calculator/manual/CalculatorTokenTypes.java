package com.torstling.bychan.calculator.manual;

import com.torstling.bychan.calculator.nodes.CalculatorNode;
import com.torstling.bychan.core.TokenType;
import com.torstling.bychan.generic.LeftParenthesisTokenType;
import com.torstling.bychan.generic.RightParenthesisTokenType;
import com.torstling.bychan.generic.WhitespaceTokenType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CalculatorTokenTypes {
    @NotNull
    public static List<TokenType<CalculatorNode>> get() {
        return Arrays.<TokenType<CalculatorNode>>asList(
                new WhitespaceTokenType<>(),
                NumberTokenType.get(),
                SubtractionTokenType.get(),
                AdditionTokenType.get(),
                MultiplicationTokenType.get(),
                LeftParenthesisTokenType.<CalculatorNode>get(),
                RightParenthesisTokenType.<CalculatorNode>get());
    }
}
