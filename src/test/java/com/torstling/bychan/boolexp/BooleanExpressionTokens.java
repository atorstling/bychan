package com.torstling.bychan.boolexp;

import com.torstling.bychan.core.TokenType;
import com.torstling.bychan.generic.LeftParenthesisTokenType;
import com.torstling.bychan.generic.RightParenthesisTokenType;
import com.torstling.bychan.generic.WhitespaceTokenType;

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
