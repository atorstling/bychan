package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.PrattParser;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Token<BooleanExpressionNode>> tokens = new Lexer<>(BooleanExpressionTokens.<BooleanSymbolTable>get()).lex(input);
        return new PrattParser<>(tokens, input).parseExpression(null, 0);
    }
}
