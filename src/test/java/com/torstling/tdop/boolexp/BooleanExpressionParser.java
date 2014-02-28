package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.ExpressionStatement;
import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.core.PrattParser;
import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode>> tokens = new Lexer<>(BooleanExpressionTokens.<BooleanSymbolTable>get()).lex(input);
        return new PrattParser<>(tokens).tryParse(null, new ExpressionStatement<>(0)).getRootNode();
    }
}
