package org.bychan.calculator.manual;

import org.bychan.calculator.nodes.CalculatorNode;
import org.bychan.core.TokenType;
import org.bychan.shared.LeftParenthesisTokenType;
import org.bychan.shared.RightParenthesisTokenType;
import org.bychan.shared.WhitespaceTokenType;
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
