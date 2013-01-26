package com.torstling.tdop;

import com.sun.org.apache.xpath.internal.ExpressionNode;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class CalculatorParserImplTest {

    @Test
    public void singleDigit() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new DigitToken(1),
                new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new DigitNode(1), rootNode);
    }

    @Test
    public void subtraction() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new DigitToken(1),
                new SubtractionToken(),
                new DigitToken(2),
                new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new SubtractionNode(new DigitNode(1), new DigitNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new LeftParenthesisToken(),
                new DigitToken(2),
                new RightParenthesisToken(),
                new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new DigitNode(2), rootNode);
    }

    @Test
    public void ambigous1() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new LeftParenthesisToken(),
                new DigitToken(1),
                new SubtractionToken(),
                new DigitToken(2),
                new RightParenthesisToken(),
                new SubtractionToken(),
                new DigitToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        SubtractionNode left = new SubtractionNode(new DigitNode(1), new DigitNode(2));
        DigitNode right = new DigitNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void ambigous2() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new DigitToken(1),
                new SubtractionToken(),
                new LeftParenthesisToken(),
                new DigitToken(2),
                new SubtractionToken(),
                new DigitToken(3),
                new RightParenthesisToken(),
                new EndToken()));
        Node rootNode = p.parse();
        DigitNode left = new DigitNode(1);
        SubtractionNode right = new SubtractionNode(new DigitNode(2), new DigitNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void prio() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new DigitToken(1),
                new SubtractionToken(),
                new DigitToken(2),
                new MultiplicationToken(),
                new DigitToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        DigitNode left = new DigitNode(1);
        MultiplicationNode right = new MultiplicationNode(new DigitNode(2), new DigitNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void prioReverse() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(
                new DigitToken(1),
                new MultiplicationToken(),
                new DigitToken(2),
                new SubtractionToken(),
                new DigitToken(3),
                new EndToken()));
        Node rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new DigitNode(1), new DigitNode(2));
        DigitNode right = new DigitNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }
}
