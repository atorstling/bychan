package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.EndLexeme;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.Lexer;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanExpressionParserTest {

    @Test
    public void lexing() {
        Lexer<BooleanExpressionNode> lexer = new Lexer<>(BooleanExpressionTokens.<BooleanSymbolTable>get());
        List<Lexeme<BooleanExpressionNode>> lexemeStream = lexer.lex("a + b");
        assertTrue(lexemeStream.get(0) instanceof VariableLexeme);
        assertTrue(lexemeStream.get(1) instanceof OrLexeme);
        assertTrue(lexemeStream.get(2) instanceof VariableLexeme);
        assertTrue(lexemeStream.get(3) instanceof EndLexeme);
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
