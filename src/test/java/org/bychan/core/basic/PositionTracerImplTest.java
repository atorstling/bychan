package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionTracerImplTest {

    @Test
    public void empty() {
        String input = "";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        TokenStack<Object> stack = new TokenStack<>(mockEndToken(input));
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), t.getParsingPosition(stack));
    }

    @Test
    public void beforeStarted() {
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
    public void onePickedUp() {
        String input = "a";
        PositionTracerImpl<Object> t = new PositionTracerImpl<>(input);
        //noinspection unchecked
        Token<Object> first = mock(Token.class);
        //noinspection unchecked
        TokenType<Object> tokenType = mock(TokenType.class);
        //noinspection unchecked
        when(first.getMatch()).thenReturn(new LexingMatch<>(0, 1, "a", tokenType));
        when(first.getType()).thenReturn(tokenType);
        TokenStack<Object> stack = new TokenStack<>(first, mockEndToken(input));
        stack.pop();
        ParsingPosition parsingPosition = t.getParsingPosition(stack);
        assertEquals(new ParsingPosition(new TextPosition(0, 1, 1), stack), parsingPosition);
    }

}