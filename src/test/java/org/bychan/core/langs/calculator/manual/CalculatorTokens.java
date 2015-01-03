package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.Token;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.shared.LeftParenthesisToken;
import org.bychan.core.langs.shared.RightParenthesisToken;
import org.bychan.core.langs.shared.WhitespaceToken;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CalculatorTokens {
    @NotNull
    public static List<Token<CalculatorNode>> get() {
        return Arrays.<Token<CalculatorNode>>asList(
                new WhitespaceToken<>(),
                NumberToken.get(),
                SubtractionToken.get(),
                AdditionToken.get(),
                MultiplicationToken.get(),
                LeftParenthesisToken.<CalculatorNode>get(),
                RightParenthesisToken.<CalculatorNode>get());
    }
}
