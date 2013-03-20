package com.torstling.tdop;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanExpressionParserTest {

    @Test
    public void lexing() {
        Lexer lexer = new Lexer<BooleanExpressionNode>(BooleanExpressionTokens.get());
        List<Token> tokenStream = lexer.lex("a | b");
        assertTrue(tokenStream.get(0) instanceof VariableToken);
        assertTrue(tokenStream.get(1) instanceof OrToken);
        assertTrue(tokenStream.get(2) instanceof VariableToken);
        assertTrue(tokenStream.get(3) instanceof EndToken);
    }

    @Test
    public void parsing() {
        BooleanExpressionParser parser = new BooleanExpressionParser();
        BooleanExpressionNode root = parser.parse("a | b");

        assertEquals(false, root.evaluate(new VariableBindingBuilder().bind("a", false).bind("b", false).build()));
        assertEquals(true, root.evaluate(new VariableBindingBuilder().bind("a", false).bind("b", true).build()));
        assertEquals(true, root.evaluate(new VariableBindingBuilder().bind("a", true).bind("b", false).build()));
        assertEquals(true, root.evaluate(new VariableBindingBuilder().bind("a", true).bind("b", true).build()));
    }
}
