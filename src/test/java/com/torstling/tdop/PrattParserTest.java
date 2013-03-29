package com.torstling.tdop;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class PrattParserTest {

    @Test
    public void singleDigit() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(1),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new NumberNode(1), rootNode);
    }

    @Test
    public void subtraction() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(1),
                new SubtractionToken(),
                new NumberToken(2),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(),
                new NumberToken(2),
                new RightParenthesisToken(),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambigous1() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(),
                new NumberToken(1),
                new SubtractionToken(),
                new NumberToken(2),
                new RightParenthesisToken(),
                new SubtractionToken(),
                new NumberToken(3),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        SubtractionNode left = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void ambigous2() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(1),
                new SubtractionToken(),
                new LeftParenthesisToken(),
                new NumberToken(2),
                new SubtractionToken(),
                new NumberToken(3),
                new RightParenthesisToken(),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void prio() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(1),
                new SubtractionToken(),
                new NumberToken(2),
                new MultiplicationToken(),
                new NumberToken(3),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void prioReverse() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(1),
                new MultiplicationToken(),
                new NumberToken(2),
                new SubtractionToken(),
                new NumberToken(3),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(1),
                new MultiplicationToken(),
                new NumberToken(2),
                new MultiplicationToken(),
                new NumberToken(3),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(left, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(),
                new NumberToken(1),
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Expected RightParenthesisToken, got EndToken: '.'", e.getMessage());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new RightParenthesisToken(),
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot use right parenthesis as prefix to expression", e.getMessage());
        }
    }

    @Test
    public void empty() {
        PrattParser<CalculatorNode> p = new PrattParser<CalculatorNode>(Arrays.<Token<CalculatorNode>>asList(
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot parse end as expression", e.getMessage());
        }
    }
}
