package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Token;
import org.bychan.core.langs.shared.LeftParenthesisToken;
import org.bychan.core.langs.shared.RightParenthesisToken;
import org.bychan.core.langs.shared.WhitespaceToken;

import java.util.Arrays;
import java.util.List;

class BooleanExpressionTokens {
    public static List<Token<BooleanExpressionNode>> get() {
        return Arrays.<Token<BooleanExpressionNode>>asList(
                new WhitespaceToken<>(),
                NotToken.get(),
                VariableToken.get(),
                AndToken.get(),
                OrToken.get(),
                LeftParenthesisToken.<BooleanExpressionNode>get(),
                RightParenthesisToken.<BooleanExpressionNode>get());
    }
}
