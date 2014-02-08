package com.torstling.tdop.fluid;


import com.torstling.tdop.calculator.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    @Test
    public void test() {
        LanguageBuilder<CalculatorNode, CalculatorSymbolTable> lb = new LanguageBuilder<>();

        final TokenDefinition<CalculatorNode, CalculatorSymbolTable> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix((parent, match, parser) -> {
                    CalculatorNode trailingExpression = parser.expression(parent);
                    parser.expectSingleToken(rparen);
                    return trailingExpression;
                }).build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .supportsPrefix((parent, match, parser) -> parser.expression(parent))
                .supportsInfix((parent, match, left, parser) -> new AdditionNode(left, parser.expression(parent)))
                .build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> minus = lb.newToken()
                .matchesString("-")
                .named("minus")
                .supportsPrefix((parent, match, parser) -> new NegationNode(parser.expression(parent)))
                .supportsInfix((parent, match, left, parser) -> new SubtractionNode(left, parser.expression(parent))).build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> number = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number")
                .supportsStandalone((parent, match) -> new NumberNode(Integer.parseInt(match.getText()))).build();
        Language<CalculatorNode, CalculatorSymbolTable> l = lb
                .newLowerPriorityLevel()
                .addToken(lparen)
                .addToken(rparen)
                .addToken(whitespace)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(plus)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(minus)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(number)
                .endLevel()
                .completeLanguage();
        assertEquals(3, l.getParser().tryParse(new RootCalculatorNode(), "1+2").getRootNode().evaluate());
        assertEquals(-1, l.getParser().tryParse(new RootCalculatorNode(), "1+-2").getRootNode().evaluate());
        assertEquals(3, l.getParser().tryParse(new RootCalculatorNode(), "1--2").getRootNode().evaluate());
    }
}
