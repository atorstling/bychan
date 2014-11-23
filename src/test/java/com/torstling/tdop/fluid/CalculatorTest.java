package com.torstling.tdop.fluid;

import com.torstling.tdop.calculator.nodes.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    @Test
    public void test() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();

        final TokenDefinition<CalculatorNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<CalculatorNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix((previous, match, parser) -> {
                    CalculatorNode trailingExpression = parser.expression(previous);
                    parser.expectSingleToken(rparen);
                    return trailingExpression;
                }).build();

        TokenDefinition<CalculatorNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<CalculatorNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .supportsPrefix((previous, match, parser) -> parser.expression(previous))
                .supportsInfix((match, previous, parser) -> new AdditionNode(previous, parser.expression(previous)))
                .build();

        TokenDefinition<CalculatorNode> minus = lb.newToken()
                .matchesString("-")
                .named("minus")
                .supportsPrefix((previous, match, parser) -> new NegationNode(parser.expression(previous)))
                .supportsInfix((match, previous, parser) -> new SubtractionNode(previous, parser.expression(previous))).build();

        TokenDefinition<CalculatorNode> number = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number")
                .supportsStandalone((previous, match) -> new NumberNode(Integer.parseInt(match.getText()))).build();
        Language<CalculatorNode> l = lb
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
        assertEquals(3, l.getParser().tryParse("1+2").getRootNode().evaluate());
        assertEquals(-1, l.getParser().tryParse("1+-2").getRootNode().evaluate());
        assertEquals(3, l.getParser().tryParse("1--2").getRootNode().evaluate());
    }


    @Test
    public void testClearerSyntax() {
        LanguageBuilder2<CalculatorNode> b = new LanguageBuilder2<>();
        Language<CalculatorNode> l = b
                .newLevelToken().named("rparen").matchesString(")")
                .newToken().named("lparen").matchesString("(")
                .newToken().named("whitespace").matchesPattern("\\s+").ignoreWhenParsing()
                .newLevelToken().named("plus").matchesString("+")
                .supportsPrefix((previous, match, parser) -> parser.expression(previous))
                .supportsInfix((match, previous, parser) -> new AdditionNode(previous, parser.expression(previous)))
                .newLevelToken().named("minus").matchesString("-")
                .supportsPrefix((previous, match, parser) -> new NegationNode(parser.expression(previous)))
                .supportsInfix((match, previous, parser) -> new SubtractionNode(previous, parser.expression(previous)))
                .newLevelToken().named("number").matchesPattern("[0-9]+")
                .supportsStandalone((previous, match) -> new NumberNode(Integer.parseInt(match.getText())))
                .completeLanguage();

        assertEquals(3, l.getParser().tryParse("1+2").getRootNode().evaluate());
        assertEquals(-1, l.getParser().tryParse("1+-2").getRootNode().evaluate());
        assertEquals(3, l.getParser().tryParse("1--2").getRootNode().evaluate());
    }

}
