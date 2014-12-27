package org.bychan.core.basic;

import org.bychan.core.langs.calculator.manual.CalculatorTokenTypes;
import org.bychan.core.langs.calculator.manual.NumberToken;
import org.bychan.core.langs.calculator.manual.NumberTokenType;
import org.bychan.core.langs.calculator.manual.SubtractionToken;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.shared.LeftParenthesisToken;
import org.bychan.core.langs.shared.RightParenthesisToken;
import junit.framework.Assert;
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
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex("(1 -) ");
        assertEquals(tokens.toString(), 5, tokens.size());
        assertTrue(tokens.get(0) instanceof LeftParenthesisToken);
        Assert.assertEquals(new NumberToken(new LexingMatch<>(0, 1, "1", NumberTokenType.get())), tokens.get(1));
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
            assertEquals("No matching rule for char-range starting at 1: ';1'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtStart() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex(";1");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 0: ';1'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtEnd() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex("1;");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 1: ';'", e.getMessage());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAlone() {
        try {
            new Lexer<>(CalculatorTokenTypes.get()).lex(";");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule for char-range starting at 0: ';'", e.getMessage());
        }
    }

    @Test
    public void abortOnMatchWithoutProgress() {
        Lexer<Integer> l = new Lexer<>(Collections.singleton(new MatchAllTokenType()));
        try {
            l.lex("a");
        } catch (LexingFailedException e) {
            assertEquals(new LexingPosition(0, "a"), e.getLexingPosition());
            assertTrue(e.getMessage().contains("did not advance lexing"));
        }
    }

    private static class MatchAllTokenType implements TokenType<Integer> {
        @NotNull
        @Override
        public Token<Integer> toToken(@NotNull LexingMatch match) {
            return new LeftParenthesisToken<>(match);
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
