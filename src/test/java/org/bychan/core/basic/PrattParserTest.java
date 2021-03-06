package org.bychan.core.basic;

import org.bychan.core.langs.calculator.manual.MultiplicationLexeme;
import org.bychan.core.langs.calculator.manual.MultiplicationNode;
import org.bychan.core.langs.calculator.manual.NumberLexeme;
import org.bychan.core.langs.calculator.manual.SubtractionLexeme;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.calculator.nodes.NumberNode;
import org.bychan.core.langs.calculator.nodes.SubtractionNode;
import org.bychan.core.langs.shared.LeftParenthesisLexeme;
import org.bychan.core.langs.shared.RightParenthesisLexeme;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrattParserTest {
    @Test
    public void singleDigit() {
        String text = "1";
        List<Lexeme<CalculatorNode>> lexemes = Arrays.<Lexeme<CalculatorNode>>asList(
                new NumberLexeme(createTestMatch(text)),
                createTestEndToken());
        PrattParser<CalculatorNode> p = createParser(lexemes);
        CalculatorNode rootNode = p.expr(null, 0);
        assertEquals(new NumberNode(1), rootNode);
    }

    @NotNull
    private <N> PrattParser<N> createParser(List<Lexeme<N>> lexemes) {
        //noinspection unchecked
        PositionTracer<N> positionTracer = mock(PositionTracer.class);
        ParsingPosition parsingPosition = mock(ParsingPosition.class);
        when(parsingPosition.toString()).thenReturn("mock position");
        when(positionTracer.getParsingPosition(any())).thenReturn(parsingPosition);
        return new PrattParser<>(lexemes, positionTracer);
    }

    private <N> EndLexeme<N> createTestEndToken() {
        return new EndLexeme<>(new LexingMatch<>(7, 8, "", EndToken.get()));
    }

    private LexingMatch createTestMatch(String text) {
        return new LexingMatch<>(0, 1, text, new MockToken());
    }

    @NotNull
    private LexingMatch nextMatch() {
        return createTestMatch("test");
    }

    @Test
    public void subtraction() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new NumberLexeme(createTestMatch("1")),
                new SubtractionLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        assertEquals(new SubtractionNode(new NumberNode(1), new NumberNode(2)), rootNode);
    }

    @Test
    public void paranthesis() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new LeftParenthesisLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                new RightParenthesisLexeme(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        assertEquals(new NumberNode(2), rootNode);
    }

    @Test
    public void ambiguous() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new LeftParenthesisLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("1")),
                new SubtractionLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                new RightParenthesisLexeme(nextMatch()),
                new SubtractionLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        SubtractionNode left = new SubtractionNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void ambiguous2() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new NumberLexeme(createTestMatch("1")),
                new SubtractionLexeme(nextMatch()),
                new LeftParenthesisLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                new SubtractionLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("3")),
                new RightParenthesisLexeme(nextMatch()),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        NumberNode left = new NumberNode(1);
        SubtractionNode right = new SubtractionNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void priority() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new NumberLexeme(createTestMatch("1")),
                new SubtractionLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                new MultiplicationLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        NumberNode left = new NumberNode(1);
        MultiplicationNode right = new MultiplicationNode(new NumberNode(2), new NumberNode(3));
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void priorityReverse() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new NumberLexeme(createTestMatch("1")),
                new MultiplicationLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                new SubtractionLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new SubtractionNode(left, right), rootNode);
    }

    @Test
    public void multipleSameOp() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new NumberLexeme(createTestMatch("1")),
                new MultiplicationLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("2")),
                new MultiplicationLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("3")),
                createTestEndToken()));
        CalculatorNode rootNode = p.expr(null, 0);
        MultiplicationNode left = new MultiplicationNode(new NumberNode(1), new NumberNode(2));
        NumberNode right = new NumberNode(3);
        assertEquals(new MultiplicationNode(left, right), rootNode);
    }

    @Test
    public void unclosedParenthesis() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new LeftParenthesisLexeme(nextMatch()),
                new NumberLexeme(createTestMatch("1")),
                createTestEndToken()));
        try {
            p.expr(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing failed: 'Expected token 'rightParenthesis', but got 'END'' @ mock position", e.getFailureInformation().toString());
        }
    }

    @Test
    public void wrongOrderParenthesis() {
        PrattParser<CalculatorNode> p = createParser(Arrays.<Lexeme<CalculatorNode>>asList(
                new RightParenthesisLexeme(nextMatch()),
                createTestEndToken()));
        try {
            p.expr(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current lexeme does not support nud parsing' @ mock position", e.getMessage());
        }
    }

    @Test
    public void empty() {
        PrattParser<CalculatorNode> p = createParser(Collections.<Lexeme<CalculatorNode>>singletonList(
                createTestEndToken()));
        try {
            p.expr(null, 0);
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Parsing failed: 'Premature end reached' @ mock position", e.getFailureInformation().toString());
        }
    }

    @Test
    public void failsWhenLedNotSupported() {
        Lexeme first = mock(Lexeme.class);
        when(first.toString()).thenReturn("firstToken");
        NudParseAction nudParseAction = mock(NudParseAction.class);
        //noinspection unchecked
        when(nudParseAction.parse(any(), any())).thenReturn("nudParsingResult");
        when(first.getNud()).thenReturn(nudParseAction);

        Lexeme second = mock(Lexeme.class);
        when(second.toString()).thenReturn("secondToken");
        when(second.getLed()).thenReturn(null);
        when(second.lbp()).thenReturn(1);
        when(second.getToken()).thenReturn(mock(Token.class));
        //noinspection unchecked
        LexingMatch<Object> match = mock(LexingMatch.class);
        when(match.getStartPosition()).thenReturn(1);
        when(second.getMatch()).thenReturn(match);

        PrattParser<Object> p = createParser(Arrays.asList(first, second));
        try {
            p.expr(5, 0);
            fail("Expected exception");
        } catch(ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current token does not support led parsing' @ mock position", e.getMessage());
        }
    }

    @Test
    public void failsWhenNudNotSupported() {
        Lexeme lexeme = mock(Lexeme.class);
        when(lexeme.getNud()).thenReturn(null);
        when(lexeme.toString()).thenReturn("bleargh");
        //noinspection unchecked
        LexingMatch<Object> match = mock(LexingMatch.class);
        when(match.getStartPosition()).thenReturn(1);
        when(lexeme.getMatch()).thenReturn(match);
        when(lexeme.getToken()).thenReturn(mock(Token.class));
        //noinspection unchecked
        PrattParser<Object> p = createParser(Collections.singletonList(lexeme));
        try {
            p.expr(null, 0);
            fail("Expected exception");
        } catch(ParsingFailedException e) {
            assertEquals("Parsing failed: 'Current lexeme does not support nud parsing' @ mock position", e.getMessage());
        }
    }
}
