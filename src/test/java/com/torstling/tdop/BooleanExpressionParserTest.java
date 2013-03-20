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

        checkTruth(root, false, false, false);
        checkTruth(root, false, true, true);
        checkTruth(root, true, false, true);
        checkTruth(root, true, true, true);
    }

    private void checkTruth(BooleanExpressionNode root, boolean aValue, boolean bValue, boolean expectedOutcome) {
        assertEquals(expectedOutcome, root.evaluate(new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build()));
    }
}
