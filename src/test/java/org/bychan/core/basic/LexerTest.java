package org.bychan.core.basic;

import junit.framework.Assert;
import org.bychan.core.langs.calculator.manual.CalculatorTokens;
import org.bychan.core.langs.calculator.manual.NumberLexeme;
import org.bychan.core.langs.calculator.manual.NumberToken;
import org.bychan.core.langs.calculator.manual.SubtractionLexeme;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.shared.LeftParenthesisLexeme;
import org.bychan.core.langs.shared.RightParenthesisLexeme;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LexerTest {
    @Test
    public void calculatorTest() {
        List<Lexeme<CalculatorNode>> lexemes = new Lexer<>(CalculatorTokens.get()).lex("(1 -) ");
        assertEquals(lexemes.toString(), 5, lexemes.size());
        assertTrue(lexemes.get(0) instanceof LeftParenthesisLexeme);
        Assert.assertEquals(new NumberLexeme(new LexingMatch<>(0, 1, "1", NumberToken.get())), lexemes.get(1));
        assertTrue(lexemes.get(2) instanceof SubtractionLexeme);
        assertTrue(lexemes.get(3) instanceof RightParenthesisLexeme);
        assertTrue(lexemes.get(4) instanceof EndLexeme);
    }

    @Test
    public void stopsAtUnknownCharacters() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex("1;1");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 1: ';1'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtStart() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex(";1");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 0: ';1'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtEnd() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex("1;");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 1: ';'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAlone() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex(";");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 0: ';'", e.getMessage());
        }
    }

    @Test
    public void abortOnMatchWithoutProgress() {
        Lexer<Integer> l = new Lexer<>(Collections.singleton(new MatchAllToken()));
        try {
            l.lex("a");
        } catch (LexingFailedException e) {
            assertEquals(new LexingPosition(0, "a"), e.getLexingPosition());
            assertTrue(e.getMessage().contains("did not advance lexing"));
        }
    }

    private static class MatchAllToken implements Token<Integer> {
        @NotNull
        @Override
        public Lexeme<Integer> toLexeme(@NotNull LexingMatch match) {
            return new LeftParenthesisLexeme<>(match);
        }

        @NotNull
        @Override
        public Pattern getPattern() {
            return Pattern.compile("");
        }

        @Override
        public boolean include() {
            return true;
        }

        @Override
        public String toString() {
            return "matchAll";
        }
    }
}
