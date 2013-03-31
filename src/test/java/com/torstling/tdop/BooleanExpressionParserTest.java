package com.torstling.tdop;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanExpressionParserTest {

    @Test
    public void lexing() {
        Lexer<BooleanExpressionNode> lexer = new Lexer<>(BooleanExpressionTokens.get());
        List<Token<BooleanExpressionNode>> tokenStream = lexer.lex("a + b");
        assertTrue(tokenStream.get(0) instanceof VariableToken);
        assertTrue(tokenStream.get(1) instanceof OrToken);
        assertTrue(tokenStream.get(2) instanceof VariableToken);
        assertTrue(tokenStream.get(3) instanceof EndToken);
    }

    @Test
    public void or() {
        BooleanExpressionNode root = parse("a + b");
        checkTruth(root, false, false, false);
        checkTruth(root, false, true, true);
        checkTruth(root, true, false, true);
        checkTruth(root, true, true, true);
    }

    @Test
    public void and() {
        BooleanExpressionNode root = parse("a * b");
        checkTruth(root, false, false, false);
        checkTruth(root, false, true, false);
        checkTruth(root, true, false, false);
        checkTruth(root, true, true, true);
    }

    @Test
    public void not() {
        BooleanExpressionNode root = parse("!a");
        assertEquals(true, root.evaluate(new VariableBindingBuilder().bind("a", false).build()));
        assertEquals(false, root.evaluate(new VariableBindingBuilder().bind("a", true).build()));

    }

    @Test
    public void priorityOfNotOperator() {
        BooleanExpressionNode root = parse("!a+b");
        checkTruth(root, false, false, true);
    }

    private BooleanExpressionNode parse(String expression) {
        BooleanExpressionParser parser = new BooleanExpressionParser();
        return parser.parse(expression);
    }

    private void checkTruth(BooleanExpressionNode root, boolean aValue, boolean bValue, boolean expectedOutcome) {
        assertEquals(expectedOutcome, root.evaluate(new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build()));
    }
}
