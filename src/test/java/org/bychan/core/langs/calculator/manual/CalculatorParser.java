package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.PrattParser;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex(input);
        return new PrattParser<>(tokens).parseExpression(null, 0);
    }
}
