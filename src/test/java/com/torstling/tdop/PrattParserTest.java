package com.torstling.tdop;

import com.torstling.tdop.calculator.*;
import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class PrattParserTest {

    public static final int TEST_END_TOKEN_POSITION = Integer.MAX_VALUE;

    @Test
    public void singleDigit() {
        String text = "1";
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch(text)),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new NumberNode(1), rootNode);
    }

    private EndToken createTestEndToken() {
        return new EndToken(new LexingMatch(Integer.MAX_VALUE, Integer.MAX_VALUE, "END"));
    }

    private LexingMatch createTestMatch(String text) {
        return new LexingMatch(0, 1, text);
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
        CalculatorNode rootNode = p.parse();
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse();
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
        CalculatorNode rootNode = p.parse();
        SubtractionNode left = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
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
        CalculatorNode rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
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
        CalculatorNode rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
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
        CalculatorNode rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
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
        CalculatorNode rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(left, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                createTestEndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing terminated at position " + TEST_END_TOKEN_POSITION + ": Expected a token of type 'RightParenthesisTokenType', but got '.'", e.getMessage());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot use right parenthesis as prefix to expression", e.getMessage());
        }
    }

    @Test
    public void empty() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                createTestEndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot parse end as expression", e.getMessage());
        }
    }
}
