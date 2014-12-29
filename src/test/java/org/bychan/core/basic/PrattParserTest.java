package org.bychan.core.basic;

import org.bychan.core.langs.calculator.manual.MultiplicationNode;
import org.bychan.core.langs.calculator.manual.MultiplicationToken;
import org.bychan.core.langs.calculator.manual.NumberToken;
import org.bychan.core.langs.calculator.manual.SubtractionToken;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.calculator.nodes.NumberNode;
import org.bychan.core.langs.calculator.nodes.SubtractionNode;
import org.bychan.core.langs.shared.LeftParenthesisToken;
import org.bychan.core.langs.shared.RightParenthesisToken;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrattParserTest {
    @Test
    public void singleDigit() {
        String text = "1";
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch(text)),
                createTestEndToken()), text);
        CalculatorNode rootNode = p.parseExpression(null, 0);
        assertEquals(new NumberNode(1), rootNode);
    }

    private EndToken createTestEndToken() {
        return new EndToken(new LexingMatch<>(7, 8, "", EndTokenType.get()));
    }

    private LexingMatch createTestMatch(String text) {
        return new LexingMatch<>(0, 1, text, new MockTokenType());
    }

    @NotNull
    private LexingMatch nextMatch() {
        return createTestMatch("test");
    }

    @Test
    public void subtraction() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void paranthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambiguous() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new RightParenthesisToken(nextMatch()),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        SubtractionNode previous = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void ambiguous2() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        NumberNode previous = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void priority() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        NumberNode previous = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void priorityReverse() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new SubtractionToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        MultiplicationNode previous = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(previous, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new NumberToken(createTestMatch("1")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("2")),
                new MultiplicationToken(nextMatch()),
                new NumberToken(createTestMatch("3")),
                createTestEndToken()),"1234567890");
        CalculatorNode rootNode = p.parseExpression(null, 0);
        MultiplicationNode previous = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(previous, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new LeftParenthesisToken(nextMatch()),
                new NumberToken(createTestMatch("1")),
                createTestEndToken()),"1234567890");
        try {
            p.parseExpression(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing failed: 'Expected a token of type 'RightParenthesisTokenType', but got 'END'' @  position 1:7 (index 6), current token is END and remaining tokens are []", e.getParsingFailedInformation().toString());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                new RightParenthesisToken(nextMatch()),
                createTestEndToken()),"1234567890");
        try {
            p.parseExpression(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current token does not support prefix parsing' @  position 1:1 (index 0), current token is ) and remaining tokens are [END]", e.getMessage());
        }
    }

    @Test
    public void empty() {
        PrattParser<CalculatorNode> p = new PrattParser<>(Arrays.<Token<CalculatorNode>>asList(
                createTestEndToken()),"1234567890");
        try {
            p.parseExpression(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current token does not support prefix parsing' @  position 1:7 (index 6), current token is END and remaining tokens are []", e.getParsingFailedInformation().toString());
        }
    }

    @Test
    public void failsWhenInfixNotSupported() {
        Token first = mock(Token.class);
        when(first.toString()).thenReturn("firstToken");
        PrefixParseAction prefixParseAction = mock(PrefixParseAction.class);
        //noinspection unchecked
        when(prefixParseAction.parse(any(), any())).thenReturn("prefixParsingResult");
        when(first.getPrefixParser()).thenReturn(prefixParseAction);

        Token second = mock(Token.class);
        when(second.toString()).thenReturn("secondToken");
        when(second.getInfixParser()).thenReturn(null);
        when(second.leftBindingPower()).thenReturn(1);
        when(second.getType()).thenReturn(mock(TokenType.class));
        //noinspection unchecked
        LexingMatch<Object> match = mock(LexingMatch.class);
        when(match.getStartPosition()).thenReturn(1);
        when(second.getMatch()).thenReturn(match);

        PrattParser<Object> p = new PrattParser<>(Arrays.asList(first, second),"1234567890");
        try {
            p.parseExpression(5, 0);
            fail("Expected exception");
        } catch(ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current token does not support infix parsing' @  position 1:2 (index 1), current token is secondToken and remaining tokens are []", e.getMessage());
        }
    }

    @Test
    public void failsWhenPrefixNotSupported() {
        Token token = mock(Token.class);
        when(token.getPrefixParser()).thenReturn(null);
        when(token.toString()).thenReturn("bleargh");
        //noinspection unchecked
        LexingMatch<Object> match = mock(LexingMatch.class);
        when(match.getStartPosition()).thenReturn(1);
        when(token.getMatch()).thenReturn(match);
        when(token.getType()).thenReturn(mock(TokenType.class));
        PrattParser<Object> p = new PrattParser<>(Arrays.asList(token),"1234567890");
        try {
            p.parseExpression(null, 0);
            fail("Expected exception");
        } catch(ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current token does not support prefix parsing' @  position 1:2 (index 1), current token is bleargh and remaining tokens are []", e.getMessage());
        }
    }

}
