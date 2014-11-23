package com.torstling.tdop.calculator.manual;

import com.torstling.tdop.calculator.nodes.CalculatorNode;
import com.torstling.tdop.core.TokenType;
import com.torstling.tdop.generic.LeftParenthesisTokenType;
import com.torstling.tdop.generic.RightParenthesisTokenType;
import com.torstling.tdop.generic.WhitespaceTokenType;
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
