package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<CalculatorNode>(CalculatorTokenTypes.get()).lex(input);
        return new CalculatorParserImpl<CalculatorNode>(tokens).parse();
    }
}
