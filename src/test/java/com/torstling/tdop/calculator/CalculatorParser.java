package com.torstling.tdop.calculator;

import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.core.PrattParser;
import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex(input);
        return new PrattParser<>(tokens).parse(new RootCalculatorNode());
    }
}
