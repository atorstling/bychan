package com.torstling.tdop;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class CalculatorParserImplTest {

    @Test
    public void singleDigit() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new NumberToken(1),
                new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new NumberNode(1), rootNode);
    }

    @Test
    public void subtraction() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new NumberToken(1),
                new SubtractionToken(),
                new NumberToken(2),
                new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new LeftParenthesisToken(),
                new NumberToken(2),
                new RightParenthesisToken(),
                new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambigous1() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new LeftParenthesisToken(),
                new NumberToken(1),
                new SubtractionToken(),
                new NumberToken(2),
                new RightParenthesisToken(),
                new SubtractionToken(),
                new NumberToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        SubtractionNode left = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void ambigous2() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new NumberToken(1),
                new SubtractionToken(),
                new LeftParenthesisToken(),
                new NumberToken(2),
                new SubtractionToken(),
                new NumberToken(3),
                new RightParenthesisToken(),
                new EndToken()));
        Node rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void prio() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new NumberToken(1),
                new SubtractionToken(),
                new NumberToken(2),
                new MultiplicationToken(),
                new NumberToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void prioReverse() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new NumberToken(1),
                new MultiplicationToken(),
                new NumberToken(2),
                new SubtractionToken(),
                new NumberToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new NumberToken(1),
                new MultiplicationToken(),
                new NumberToken(2),
                new MultiplicationToken(),
                new NumberToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(left, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new LeftParenthesisToken(),
                new NumberToken(1),
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Expected RightParenthesisToken, got EndToken", e.getMessage());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
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
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot parse end as expression", e.getMessage());
        }
    }
}
