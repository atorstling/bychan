package com.torstling.tdop.calculator;

import com.torstling.tdop.calculator.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class CalculatorParserTest {

    @Test
    public void test() {
        CalculatorNode rootNode = new CalculatorParser().parse("( (1-  2)* 3) -4");
        NumberNode topRight = new NumberNode(4);
        NumberNode secondRight = new NumberNode(3);
        SubtractionNode secondLeft = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        assertEquals(new SubtractionNode(new MultiplicationNode(secondLeft, secondRight), topRight), rootNode);
        assertEquals(rootNode.evaluate(), -7);
    }

    @Test
    public void test2() {
        CalculatorNode rootNode = new CalculatorParser().parse("1-2*3");
        assertEquals(rootNode.evaluate(), -5);
    }

    @Test
    public void test3() {
        CalculatorNode rootNode = new CalculatorParser().parse("99+1");
        assertEquals(rootNode.evaluate(), 100);
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
