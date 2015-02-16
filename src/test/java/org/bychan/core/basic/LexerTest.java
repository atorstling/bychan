package org.bychan.core.basic;

import junit.framework.Assert;
import org.bychan.core.dynamic.RegexMatcher;
import org.bychan.core.dynamic.TokenMatcher;
import org.bychan.core.langs.calculator.manual.CalculatorTokens;
import org.bychan.core.langs.calculator.manual.NumberLexeme;
import org.bychan.core.langs.calculator.manual.NumberToken;
import org.bychan.core.langs.calculator.manual.SubtractionLexeme;
import org.bychan.core.langs.calculator.nodes.CalculatorNode;
import org.bychan.core.langs.shared.LeftParenthesisLexeme;
import org.bychan.core.langs.shared.RightParenthesisLexeme;
import org.bychan.core.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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
            assertEquals("No matching rule @  position 1:2 (index 1), remaining text is ';1'", e.toString());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtStart() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex(";1");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule @  position 1:1 (index 0), remaining text is ';1'", e.toString());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAtEnd() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex("1;");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule @  position 1:2 (index 1), remaining text is ';'", e.toString());
        }
    }

    @Test
    public void stopsAtUnknownCharactersAlone() {
        try {
            new Lexer<>(CalculatorTokens.get()).lex(";");
            fail("expected exception");
        } catch (LexingFailedException e) {
            assertEquals("No matching rule @  position 1:1 (index 0), remaining text is ';'", e.toString());
        }
    }

    @Test
    public void abortOnMatchWithoutProgress() {
        Lexer<Integer> l = new Lexer<>(Collections.singleton(new MatchAllToken()));
        try {
            l.lex("a");
        } catch (LexingFailedException e) {
            assertEquals(new LexingPosition(StringUtils.getTextPosition("a", 0), "a"), e.getLexingPosition());
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
        public TokenMatcher getMatcher() {
            return new RegexMatcher("");
        }

        @Override
        public boolean keepAfterLexing() {
            return true;
        }

        @Override
        public String toString() {
            return "matchAll";
        }
    }
}
