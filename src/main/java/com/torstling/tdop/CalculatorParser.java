package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class CalculatorParser {
    public void parse(@NotNull final String input) {
        ArrayList<Token> tokens = new Lexer().lex(input);
        Node rootNode = new CalculatorParserImpl(tokens).parse();
    }
}
