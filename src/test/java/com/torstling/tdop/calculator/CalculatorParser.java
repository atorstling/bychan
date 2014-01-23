package com.torstling.tdop.calculator;

import com.torstling.tdop.core.ExpressionParserStrategy;
import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.core.PrattParser;
import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode, CalculatorSymbolTable>> tokens = new Lexer<CalculatorNode, CalculatorSymbolTable>(CalculatorTokenTypes.<CalculatorSymbolTable>get()).lex(input);
        final RootCalculatorNode rootCalculatorNode = new RootCalculatorNode();
        return new PrattParser<>(tokens).parse(new ExpressionParserStrategy<CalculatorNode, CalculatorSymbolTable>(rootCalculatorNode, 0));
    }
}
