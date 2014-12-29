package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionTracerImplTest {

    @Test
    public void emptyNonePopped() {
        String input = "";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        TokenStack<Object> stack = new TokenStack<>(mockEndToken(input));
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), t.getParsingPosition(stack));
    }

    @Test
    public void emptyEndPopped() {
        String input = "";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        TokenStack<Object> stack = new TokenStack<>(mockEndToken(input));
        stack.pop();
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), t.getParsingPosition(stack));
    }

    @Test
    public void singleNonePopped() {
        String input = "a";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        TokenStack<Object> stack = new TokenStack<>(mockEndToken(input));
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), t.getParsingPosition(stack));
    }

    @NotNull
    private <N> Token<N> mockEndToken(@NotNull String input) {
        return Lexer.makeEndToken(input);
    }

    @Test
    public void singleOnePopped() {
        String input = "a";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        Token<Object> first = mockToken(input, 0);
        TokenStack<Object> stack = new TokenStack<>(first, mockEndToken(input));
        stack.pop();
        ParsingPosition parsingPosition = t.getParsingPosition(stack);
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), parsingPosition);
    }

    @Test
    public void singleEndPopped() {
        String input = "a";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        Token<Object> first = mockToken(input, 0);
        TokenStack<Object> stack = new TokenStack<>(first, mockEndToken(input));
        stack.pop();
        stack.pop();
        ParsingPosition parsingPosition = t.getParsingPosition(stack);
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), parsingPosition);
    }

    @Test
    public void dualTwoPopped() {
        String input = "ab";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        Token<Object> first = mockToken(input, 0);
        Token<Object> second = mockToken(input, 1);
        TokenStack<Object> stack = new TokenStack<>(first, second, mockEndToken(input));
        stack.pop();
        stack.pop();
        ParsingPosition parsingPosition = t.getParsingPosition(stack);
        assertEquals(new ParsingPosition(new TextPosition(1, 1, 2), stack), parsingPosition);
    }

    @Test
    public void dualEndPopped() {
        String input = "ab";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        Token<Object> first = mockToken(input, 0);
        Token<Object> second = mockToken(input, 1);
        TokenStack<Object> stack = new TokenStack<>(first, second, mockEndToken(input));
        stack.pop();
        stack.pop();
        stack.pop();
        ParsingPosition parsingPosition = t.getParsingPosition(stack);
        assertEquals(new ParsingPosition(new TextPosition(1, 1, 2), stack), parsingPosition);
    }

    private Token<Object> mockToken(String input, int startPosition) {
        //noinspection unchecked
        Token<Object> first = mock(Token.class);
        //noinspection unchecked
        TokenType<Object> tokenType = mock(TokenType.class);
        //noinspection unchecked
        when(first.getMatch()).thenReturn(new LexingMatch<>(startPosition, startPosition+1, input, tokenType));
        when(first.getType()).thenReturn(tokenType);
        return first;
    }

}