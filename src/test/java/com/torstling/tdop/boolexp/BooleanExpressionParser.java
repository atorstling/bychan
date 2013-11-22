package com.torstling.tdop.boolexp;

import com.torstling.tdop.Lexer;
import com.torstling.tdop.PrattParser;
import com.torstling.tdop.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode>> tokens = new Lexer<>(BooleanExpressionTokens.get()).lex(input);
        return new PrattParser<>(tokens).parse();
    }
}
