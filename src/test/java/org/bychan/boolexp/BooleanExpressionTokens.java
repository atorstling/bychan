package org.bychan.boolexp;

import org.bychan.core.TokenType;
import org.bychan.shared.LeftParenthesisTokenType;
import org.bychan.shared.RightParenthesisTokenType;
import org.bychan.shared.WhitespaceTokenType;

import java.util.Arrays;
import java.util.List;

class BooleanExpressionTokens {
    public static <S> List<TokenType<BooleanExpressionNode>> get() {
        return Arrays.<TokenType<BooleanExpressionNode>>asList(
                new WhitespaceTokenType<>(),
                NotTokenType.<S>get(),
                VariableTokenType.<S>get(),
                AndTokenType.<S>get(),
                OrTokenType.<S>get(),
                LeftParenthesisTokenType.<BooleanExpressionNode>get(),
                RightParenthesisTokenType.<BooleanExpressionNode>get());
    }
}
