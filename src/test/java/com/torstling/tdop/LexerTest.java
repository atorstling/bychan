package com.torstling.tdop;

import com.torstling.tdop.calculator.CalculatorNode;
import com.torstling.tdop.calculator.CalculatorTokenTypes;
import com.torstling.tdop.calculator.NumberToken;
import com.torstling.tdop.calculator.SubtractionToken;
import com.torstling.tdop.core.*;
import com.torstling.tdop.generic.LeftParenthesisToken;
import com.torstling.tdop.generic.RightParenthesisToken;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LexerTest {
    @Test
    public void calculatorTest() {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex("(1 -) ");
        assertEquals(5, tokens.size());
        assertTrue(tokens.get(0) instanceof LeftParenthesisToken);
        Assert.assertEquals(new NumberToken(new LexingMatch(0, 1, "1")), tokens.get(1));
        assertTrue(tokens.get(2) instanceof SubtractionToken);
        assertTrue(tokens.get(3) instanceof RightParenthesisToken);
        assertTrue(tokens.get(4) instanceof EndToken);
    }

    @Test
    public void stopsAtUnknownCharacters() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex("1;1");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range from 1 to 2: ';'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtStart() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex(";1");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range from 0 to 1: ';'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtEnd() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex("1;");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range from 1 to 2: ';'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAlone() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex(";");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range from 0 to 1: ';'", e.getMessage());
        }
    }
}
