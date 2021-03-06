package org.bychan.core.langs.calculator.manual;

import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.calculator.nodes.NumberNode;
import org.bychan.core.langs.calculator.nodes.SubtractionNode;
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
        SubtractionNode secondleft = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        assertEquals(new SubtractionNode(new MultiplicationNode(secondleft, secondRight), topRight), rootNode);
        assertEquals((int) rootNode.evaluate(), -7);
    }

    @Test
    public void test2() {
        CalculatorNode rootNode = new CalculatorParser().parse("1-2*3");
        assertEquals((int) rootNode.evaluate(), -5);
    }

    @Test
    public void test3() {
        CalculatorNode rootNode = new CalculatorParser().parse("99+1");
        assertEquals((int) rootNode.evaluate(), 100);
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
