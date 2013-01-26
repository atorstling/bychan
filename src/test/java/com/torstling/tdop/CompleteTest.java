package com.torstling.tdop;

import com.torstling.tdop.CalculatorParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompleteTest {

    @Test
    public void test() {
        Node rootNode = new CalculatorParser().parse("((1-2)-3)-4");
        DigitNode topRight = new DigitNode(4);
        DigitNode secondRight = new DigitNode(3);
        SubtractionNode secondLeft = new SubtractionNode(new DigitNode(1), new DigitNode(2));
        assertEquals(rootNode, new SubtractionNode(new SubtractionNode(secondLeft, secondRight), topRight));
        assertEquals(rootNode.evaluate(), -8);
    }
}
