package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.ExpressionParserStrategy;
import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.core.PrattParser;
import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode, BooleanSymbolTable>> tokens = new Lexer<>(BooleanExpressionTokens.<BooleanSymbolTable>get()).lex(input);
        final BooleanExpressionRootNode booleanExpressionRootNode = new BooleanExpressionRootNode();
        return new PrattParser<>(tokens).tryParse(new ExpressionParserStrategy<BooleanExpressionNode, BooleanSymbolTable>(booleanExpressionRootNode, 0)).getRootNode();
    }
}
