package com.torstling.tdop;

import com.torstling.tdop.calculator.*;
import com.torstling.tdop.core.EndToken;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.PrattParser;
import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class PrattParserTest {

    @Test
    public void singleDigit() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(new LexingMatch("1")),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new NumberNode(1), rootNode);
    }

    @NotNull
    private LexingMatch nextMatch() {
        return new LexingMatch("test");
    }

    @Test
    public void subtraction() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(new LexingMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new RightParenthesisToken(nextMatch()),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambiguous() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(new LexingMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new RightParenthesisToken(nextMatch()),
                new SubtractionToken(nextMatch()),
                new NumberToken(new LexingMatch("3")),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        SubtractionNode left = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void ambiguous2() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(new LexingMatch("1")),
                new SubtractionToken(nextMatch()),
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(new LexingMatch("3")),
                new RightParenthesisToken(nextMatch()),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void priority() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(new LexingMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(new LexingMatch("3")),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        NumberNode left = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void priorityReverse() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(new LexingMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(new LexingMatch("3")),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(new LexingMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(new LexingMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(new LexingMatch("3")),
                new EndToken()));
        CalculatorNode rootNode = p.parse();
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(left, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(new LexingMatch("1")),
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Expected a token of type RightParenthesisTokenType, but got '.'", e.getMessage());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new RightParenthesisToken(nextMatch()),
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
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new EndToken()));
        try {
            p.parse();
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot parse end as expression", e.getMessage());
        }
    }
}
