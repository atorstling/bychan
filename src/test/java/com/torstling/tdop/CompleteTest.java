package com.torstling.tdop;

import com.torstling.tdop.CalculatorParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    @Test
    public void test2() {
        Node rootNode = new CalculatorParser().parse("1-2*3");
        assertEquals(rootNode.evaluate(), -5);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to calculator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(new CalculatorParser().parse(line).evaluate());
        }
    }
}
