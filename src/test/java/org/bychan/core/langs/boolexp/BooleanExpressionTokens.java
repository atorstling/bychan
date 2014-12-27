package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.TokenType;
import org.bychan.core.langs.shared.LeftParenthesisTokenType;
import org.bychan.core.langs.shared.RightParenthesisTokenType;
import org.bychan.core.langs.shared.WhitespaceTokenType;

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
