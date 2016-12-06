package org.bychan.core.langs.calculator;

import org.bychan.core.basic.ParsingFailedException;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinition;
import org.bychan.core.langs.calculator.nodes.*;
import org.bychan.core.utils.TextPosition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CalculatorTest {
    public static void main(String[] args) {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        l.repl(p -> p.expr(null, 0)).run();
    }

    @Test
    public void test() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();

        final TokenDefinition<CalculatorNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        lb.newToken()
                .matchesString("(")
                .named("lparen")
                .nud((left, parser, lexeme) -> {
                    CalculatorNode trailingExpression = parser.expr(left, lexeme.lbp());
                    parser.swallow("rparen");
                    return trailingExpression;
                }).build();

        lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .discardAfterLexing()
                .build();

        lb.newToken()
                .matchesString("+")
                .named("plus")
                .nud((left, parser, lexeme) -> parser.expr(left, lexeme.lbp()))
                .led((left, parser, lexeme) -> new AdditionNode(left, parser.expr(left, lexeme.lbp())))
                .build();

        lb.newToken()
                .matchesString("-")
                .named("minus")
                .nud((left, parser, lexeme) -> new NegationNode(parser.expr(left, lexeme.lbp())))
                .led((left, parser, lexeme) -> new SubtractionNode(left, parser.expr(left, lexeme.lbp()))).build();

        lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number").nud((left, parser, lexeme) -> new NumberNode(Integer.parseInt(lexeme.text()))).build();
        Language<CalculatorNode> l = lb.build();
        assertEquals(3, (int) l.newLexParser().tryParse("1+2", p2 -> p2.expr(null, 0)).root().evaluate());
        assertEquals(-1, (int) l.newLexParser().tryParse("1+-2", p1 -> p1.expr(null, 0)).root().evaluate());
        assertEquals(3, (int) l.newLexParser().tryParse("1--2", p -> p.expr(null, 0)).root().evaluate());
    }

    @Test
    public void testClearerSyntax() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();
        lb.newToken().named("rparen").matchesString(")").build();
        lb.newToken().named("lparen").matchesString("(").build();
        lb.newToken().named("whitespace").matchesPattern("\\s+").discardAfterLexing().build();
        lb.newToken().named("plus").matchesString("+")
                .led((left, parser, lexeme) -> new AdditionNode(left, parser.expr(left, lexeme.lbp()))).build();
        lb.newToken().named("minus").matchesString("-")
                .nud((left, parser, lexeme) -> new NegationNode(parser.expr(left, lexeme.lbp())))
                .led((left, parser, lexeme) -> new SubtractionNode(left, parser.expr(left, lexeme.lbp()))).build();
        lb.newToken().named("number").matchesPattern("[0-9]+").nud((left, parser, lexeme) -> new NumberNode(Integer.parseInt(lexeme.text()))).build();
        Language<CalculatorNode> l = lb.build();

        assertEquals(3, (int) l.newLexParser().tryParse("1+2", p2 -> p2.expr(null, 0)).root().evaluate());
        assertEquals(-1, (int) l.newLexParser().tryParse("1+-2", p1 -> p1.expr(null, 0)).root().evaluate());
        assertEquals(3, (int) l.newLexParser().tryParse("1--2", p -> p.expr(null, 0)).root().evaluate());
    }

    @Test
    public void testDirectCalculation() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        assertEquals(Integer.valueOf(7), l.newLexParser().tryParse("1 + 2 * 3", p -> p.expr(null, 0)).root());
    }

    @Test
    public void emptyInput() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        try {
            l.newLexParser().tryParse("", p -> p.expr(null, 0)).root();
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Premature end reached", e.getFailureInformation().toParsingFailedInformation().getFailureMessage());
            assertEquals(new TextPosition(0, 1, 1), e.getFailureInformation().toParsingFailedInformation().getParsingPosition().getTextPosition());
        }
    }

}
