package com.torstling.tdop.calculator.manual;

import com.torstling.tdop.calculator.nodes.CalculatorNode;
import com.torstling.tdop.core.Expression;
import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.core.PrattParser;
import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex(input);
        return new PrattParser<>(tokens).tryParse(null, new Expression<>(0)).getRootNode();
    }
}