package com.torstling.tdop;

import com.sun.org.apache.xpath.internal.ExpressionNode;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class CalculatorParserImplTest {

    @Test
    public void singleDigit() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(new DigitToken(1), new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new DigitNode(1), rootNode);
    }

    @Test
    public void subtraction() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(new DigitToken(1), new SubtractionToken(), new DigitToken(2), new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new SubtractionNode(new DigitNode(1), new DigitNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        CalculatorParserImpl p = new CalculatorParserImpl(Arrays.asList(new LeftParenthesisToken(), new DigitToken(2), new RightParenthesisToken(), new EndToken()));
        Node rootNode = p.parse();
        assertEquals(new ExpressionNode(new DigitNode(2)), rootNode);
    }
}
