package com.torstling.tdop.boolexp;

import com.torstling.tdop.RightParenthesisTokenType;
import com.torstling.tdop.TokenType;
import com.torstling.tdop.calculator.LeftParenthesisTokenType;

import java.util.Arrays;
import java.util.List;

class BooleanExpressionTokens {
    public static List<TokenType<BooleanExpressionNode>> get() {
        return Arrays.asList(NotTokenType.get(), VariableTokenType.get(), AndTokenType.get(), OrTokenType.get(), LeftParenthesisTokenType.<BooleanExpressionNode>get(), RightParenthesisTokenType.<BooleanExpressionNode>get());
    }
}
