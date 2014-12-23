package com.torstling.bychan.boolexp;

import com.torstling.bychan.core.Expression;
import com.torstling.bychan.core.Lexer;
import com.torstling.bychan.core.PrattParser;
import com.torstling.bychan.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode>> tokens = new Lexer<>(BooleanExpressionTokens.<BooleanSymbolTable>get()).lex(input);
        return new PrattParser<>(tokens).tryParseExpression(null, 0).getRootNode();
    }
}
