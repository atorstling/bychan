package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.PrattParser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class BooleanExpressionParser {
    @NotNull
    public BooleanExpressionNode parse(@NotNull final String input) {
        List<Lexeme<BooleanExpressionNode>> lexemes = new Lexer<>(BooleanExpressionTokens.<BooleanSymbolTable>get()).lex(input);
        return new PrattParser<>(lexemes, input).expr(null, 0);
    }
}
