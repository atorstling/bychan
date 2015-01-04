package org.bychan.core.dynamic;

import org.bychan.core.basic.ParsingFailedException;
import org.bychan.core.langs.calculator.nodes.*;
import org.bychan.core.utils.TextPosition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CalculatorTest {
    @Test
    public void test() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();

        final TokenDefinition<CalculatorNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<CalculatorNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .nud((previous, parser, lexeme) -> {
                    CalculatorNode trailingExpression = parser.subExpression();
                    parser.expectSingleLexeme(rparen.getKey());
                    return trailingExpression;
                }).build();

        TokenDefinition<CalculatorNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<CalculatorNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .nud((previous, parser, lexeme) -> parser.subExpression())
                .led((previous, parser, lexeme) -> new AdditionNode(previous, parser.subExpression()))
                .build();

        TokenDefinition<CalculatorNode> minus = lb.newToken()
                .matchesString("-")
                .named("minus")
                .nud((previous, parser, lexeme) -> new NegationNode(parser.subExpression()))
                .led((previous, parser, lexeme) -> new SubtractionNode(previous, parser.subExpression())).build();

        TokenDefinition<CalculatorNode> number = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number").nud((previous, parser, lexeme) -> new NumberNode(Integer.parseInt(lexeme.getText()))).build();
        Language<CalculatorNode> l = lb
                .addToken(lparen)
                .addToken(rparen)
                .addToken(whitespace)
                .addToken(plus)
                .addToken(minus)
                .addToken(number)
                .completeLanguage();
        assertEquals(3, l.getLexParser().tryParse("1+2").getRootNode().evaluate());
        assertEquals(-1, l.getLexParser().tryParse("1+-2").getRootNode().evaluate());
        assertEquals(3, l.getLexParser().tryParse("1--2").getRootNode().evaluate());
    }


    @Test
    public void testClearerSyntax() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();
        Language<CalculatorNode> l = lb.newToken().named("rparen").matchesString(")").end()
                .newToken().named("lparen").matchesString("(").end()
                .newToken().named("whitespace").matchesPattern("\\s+").ignoreWhenParsing().end()
                .newToken().named("plus").matchesString("+")
                .led((previous, parser, lexeme) -> new AdditionNode(previous, parser.subExpression())).end()
                .newToken().named("minus").matchesString("-")
                .nud((previous, parser, lexeme) -> new NegationNode(parser.subExpression()))
                .led((previous, parser, lexeme) -> new SubtractionNode(previous, parser.subExpression())).end()
                .newToken().named("number").matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> new NumberNode(Integer.parseInt(lexeme.getText()))).end()
                .completeLanguage();

        assertEquals(3, l.getLexParser().tryParse("1+2").getRootNode().evaluate());
        assertEquals(-1, l.getLexParser().tryParse("1+-2").getRootNode().evaluate());
        assertEquals(3, l.getLexParser().tryParse("1--2").getRootNode().evaluate());
    }

    @Test
    public void testDirectCalculation() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        assertEquals(Integer.valueOf(7), l.getLexParser().tryParse("1 + 2 * 3").getRootNode());
    }

    @Test
    public void emptyInput() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        try {
            l.getLexParser().parse("");
            fail("expected exception");
        } catch (ParsingFailedException e) {
            assertEquals("Premature end reached", e.getParsingFailedInformation().getFailedAfterLexingInformation().getFailureMessage());
            assertEquals(new TextPosition(0, 1, 1), e.getParsingFailedInformation().getFailedAfterLexingInformation().getParsingPosition().getTextPosition());
        }
    }

    public static void main(String[] args) {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        l.repl().run();
    }

}
