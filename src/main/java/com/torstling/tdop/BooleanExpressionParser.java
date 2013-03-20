package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode>> tokens = new Lexer(BooleanExpressionTokens.get()).lex(input);
        return new CalculatorParserImpl<BooleanExpressionNode>(tokens).parse();
    }
}
