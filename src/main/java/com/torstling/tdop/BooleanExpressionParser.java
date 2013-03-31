package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode>> tokens = new Lexer<BooleanExpressionNode>(BooleanExpressionTokens.get()).lex(input);
        return new PrattParser<BooleanExpressionNode>(tokens).parse();
    }
}
