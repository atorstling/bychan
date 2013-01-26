package com.torstling.tdop;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class LexerTest {
    @Test
    public void test() {
        ArrayList<Token> tokens = new Lexer().lex("(1 -) ");
        assertEquals(5, tokens.size());
        assertTrue(tokens.get(0) instanceof LeftParenthesisToken);
        assertEquals(new DigitToken(1), tokens.get(1));
        assertTrue(tokens.get(2) instanceof SubtractionToken);
        assertTrue(tokens.get(3) instanceof RightParenthesisToken);
        assertTrue(tokens.get(4) instanceof EndToken);
    }
}
