package com.torstling.tdop;

import com.torstling.tdop.calculator.*;
import com.torstling.tdop.core.EndToken;
import com.torstling.tdop.core.Lexer;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class LexerTest {
    @Test
    public void test() {
        List<Token<CalculatorNode>> tokens = new Lexer<>(CalculatorTokenTypes.get()).lex("(1 -) ");
        assertEquals(5, tokens.size());
        assertTrue(tokens.get(0) instanceof LeftParenthesisToken);
        Assert.assertEquals(new NumberToken(new LexingMatch(0, 1, "1")), tokens.get(1));
        assertTrue(tokens.get(2) instanceof SubtractionToken);
        assertTrue(tokens.get(3) instanceof RightParenthesisToken);
        assertTrue(tokens.get(4) instanceof EndToken);
    }
}
