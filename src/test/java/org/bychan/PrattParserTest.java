package org.bychan;

import org.bychan.calculator.manual.MultiplicationNode;
import org.bychan.calculator.manual.MultiplicationToken;
import org.bychan.calculator.manual.NumberToken;
import org.bychan.calculator.manual.SubtractionToken;
import org.bychan.calculator.nodes.CalculatorNode;
import org.bychan.calculator.nodes.NumberNode;
import org.bychan.calculator.nodes.SubtractionNode;
import org.bychan.core.*;
import org.bychan.generic.LeftParenthesisToken;
import org.bychan.generic.RightParenthesisToken;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class PrattParserTest {
    @Test
    public void singleDigit() {
        String text = "1";
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch(text)),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        assertEquals(new NumberNode(1), rootNode);
    }

    private EndToken createTestEndToken() {
        return new EndToken(new LexingMatch<>(Integer.MAX_VALUE, Integer.MAX_VALUE, "", EndTokenType.get()));
    }

    private LexingMatch createTestMatch(String text) {
        return new LexingMatch<>(0, 1, text, new MockTokenType());
    }

    @NotNull
    private LexingMatch nextMatch() {
        return createTestMatch("test");
    }

    @Test
    public void subtraction() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void paranthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambiguous() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        SubtractionNode previous = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void ambiguous2() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        NumberNode previous = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void priority() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        NumberNode previous = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void priorityReverse() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        MultiplicationNode previous = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parseExpression(null, 0);
        MultiplicationNode previous = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(previous, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                createTestEndToken()));
        try {
            p.parseExpression(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals(ParsingFailedInformation.forFailedAfterLexing("Expected a token of type 'RightParenthesisTokenType', but got 'END'", new ParsingPosition(2147483647)), e.getParsingFailedInformation());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        try {
            p.parseExpression(null, 0);
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot use right paranthesis as prefix to subExpression", e.getMessage());
        }
    }

    @Test
    public void empty() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                createTestEndToken()));
        try {
            p.parseExpression(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals(ParsingFailedInformation.forFailedAfterLexing("Cannot parse subExpression, end reached", new ParsingPosition(2147483647)), e.getParsingFailedInformation());
        }
    }

}
