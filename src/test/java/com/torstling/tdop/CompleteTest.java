package com.torstling.tdop;

import com.torstling.tdop.CalculatorParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompleteTest {

    @Test
    public void test() {
        Node rootNode = new CalculatorParser().parse("( (1-  2)* 3) -4");
        DigitNode topRight = new DigitNode(4);
        DigitNode secondRight = new DigitNode(3);
        SubtractionNode secondLeft = new SubtractionNode(new DigitNode(1), new DigitNode(2));
        assertEquals(new SubtractionNode(new MultiplicationNode(secondLeft, secondRight), topRight), rootNode);
        assertEquals(rootNode.evaluate(), -7);
    }
}
