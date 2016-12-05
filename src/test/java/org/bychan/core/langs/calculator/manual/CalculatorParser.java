package org.bychan.core.langs.calculator.manual;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.Lexer;
import org.bychan.core.basic.PrattParser;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Lexeme<CalculatorNode>> lexemes = new Lexer<>(CalculatorTokens.get()).lex(input);
        return new PrattParser<>(lexemes, input).expression(null, 0);
    }
}
