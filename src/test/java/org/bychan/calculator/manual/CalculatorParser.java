package org.bychan.calculator.manual;

import org.bychan.calculator.nodes.CalculatorNode;
import org.bychan.core.Lexer;
import org.bychan.core.PrattParser;
import org.bychan.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex(input);
        return new PrattParser<>(tokens).parseExpression(null, 0);
    }
}
