package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.TokenType;
import com.torstling.tdop.generic.LeftParenthesisTokenType;
import com.torstling.tdop.generic.RightParenthesisTokenType;
import com.torstling.tdop.generic.WhitespaceTokenType;

import java.util.Arrays;
import java.util.List;

class BooleanExpressionTokens {
    public static List<TokenType<BooleanExpressionNode>> get() {
        return Arrays.asList(
                new WhitespaceTokenType<BooleanExpressionNode>("\\s+"),
                NotTokenType.get(),
                VariableTokenType.get(),
                AndTokenType.get(),
                OrTokenType.get(),
                LeftParenthesisTokenType.<BooleanExpressionNode>get(),
                RightParenthesisTokenType.<BooleanExpressionNode>get());
    }
}
