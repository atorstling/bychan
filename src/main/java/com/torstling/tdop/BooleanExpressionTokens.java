package com.torstling.tdop;

import java.util.Arrays;
import java.util.List;

public class BooleanExpressionTokens {
    public static List<TokenType> get() {
        return Arrays.asList(VariableTokenType.get(), AndTokenType.get(), OrTokenType.get(), LeftParenthesisTokenType.get(), RightParenthesisTokenType.get());
    }
}
