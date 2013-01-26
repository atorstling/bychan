package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CalculatorParser {
    @NotNull
    public Node parse(@NotNull final String input) {
        List<Token> tokens = new Lexer().lex(input);
        return new CalculatorParserImpl(tokens).parse();
    }
}
