package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<CalculatorNode>(CalculatorTokenTypes.get()).lex(input);
        return new PrattParser<CalculatorNode>(tokens).parse();
    }
}
