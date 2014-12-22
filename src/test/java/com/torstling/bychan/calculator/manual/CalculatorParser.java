package com.torstling.bychan.calculator.manual;

import com.torstling.bychan.calculator.nodes.CalculatorNode;
import com.torstling.bychan.core.Expression;
import com.torstling.bychan.core.Lexer;
import com.torstling.bychan.core.PrattParser;
import com.torstling.bychan.core.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class CalculatorParser {
    @NotNull
    public CalculatorNode parse(@NotNull final String input) {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex(input);
        return new PrattParser<>(tokens).tryParse(null, new Expression<>(0)).getRootNode();
    }
}
