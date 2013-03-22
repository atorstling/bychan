package com.torstling.tdop;

import java.util.Arrays;
import java.util.List;

public class BooleanExpressionTokens {
    public static List<TokenType<BooleanExpressionNode>> get() {
        return Arrays.<TokenType<BooleanExpressionNode>>asList(NotTokenType.get(), VariableTokenType.get(), AndTokenType.get(), OrTokenType.get(), LeftParenthesisTokenType.<BooleanExpressionNode>get(), RightParenthesisTokenType.<BooleanExpressionNode>get());
    }
}
