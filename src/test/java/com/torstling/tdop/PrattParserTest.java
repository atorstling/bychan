package com.torstling.tdop;

import com.torstling.tdop.calculator.*;
import com.torstling.tdop.core.*;
import com.torstling.tdop.generic.LeftParenthesisToken;
import com.torstling.tdop.generic.RightParenthesisToken;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class PrattParserTest {
    @Test
    public void singleDigit() {
        String text = "1";
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new NumberToken(createTestMatch(text)),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        assertEquals(new NumberNode(1), rootNode);
    }

    private EndToken createTestEndToken() {
        return new EndToken(new LexingMatch(Integer.MAX_VALUE, Integer.MAX_VALUE, "END"));
    }

    private LexingMatch createTestMatch(String text) {
        return new LexingMatch(0, 1, text);
    }

    @NotNull
    private LexingMatch nextMatch() {
        return createTestMatch("test");
    }

    @Test
    public void subtraction() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void parenthesis() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambiguous() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        SubtractionNode left = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void ambiguous2() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        NumberNode left = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void priority() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        NumberNode left = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void priorityReverse() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new NumberToken(createTestMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new NumberToken(createTestMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.parse(new RootCalculatorNode());
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(left, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                createTestEndToken()));
        try {
            p.parse(new RootCalculatorNode());
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing terminated at lexing match LexingMatch{startPosition=2147483647, endPosition=2147483647, text='END'}: Expected a token of type 'RightParenthesisTokenType', but got 'END'", e.getMessage());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()));
        try {
            p.parse(new RootCalculatorNode());
            fail("expected exception");
        } catch (IllegalStateException e) {
            assertEquals("Cannot use right parenthesis as prefix to expression", e.getMessage());
        }
    }

    @Test
    public void empty() {
        PrattParser<CalculatorNode, CalculatorSymbolTable> p = new PrattParser<>(Arrays.<Token<CalculatorNode, CalculatorSymbolTable>>asList(
                createTestEndToken()));
        try {
            p.parse(new RootCalculatorNode());
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing terminated at lexing match LexingMatch{startPosition=2147483647, endPosition=2147483647, text='END'}: Cannot parse expression, end reached", e.getMessage());
        }
    }
}
