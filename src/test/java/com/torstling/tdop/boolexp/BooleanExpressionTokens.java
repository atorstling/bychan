package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.TokenType;
import com.torstling.tdop.generic.LeftParenthesisTokenType;
import com.torstling.tdop.generic.RightParenthesisTokenType;
import com.torstling.tdop.generic.WhitespaceTokenType;

import java.util.Arrays;
import java.util.List;

class BooleanExpressionTokens {
    public static <S> List<TokenType<BooleanExpressionNode, S>> get() {
        return Arrays.<TokenType<BooleanExpressionNode, S>>asList(
                new WhitespaceTokenType<>("\\s+"),
                NotTokenType.<S>get(),
                VariableTokenType.<S>get(),
                AndTokenType.<S>get(),
                OrTokenType.<S>get(),
                LeftParenthesisTokenType.<BooleanExpressionNode, S>get(),
                RightParenthesisTokenType.<BooleanExpressionNode, S>get());
    }
}
