package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.basic.TokenType;
import org.bychan.core.langs.shared.LeftParenthesisTokenType;
import org.bychan.core.langs.shared.RightParenthesisTokenType;
import org.bychan.core.langs.shared.WhitespaceTokenType;
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
