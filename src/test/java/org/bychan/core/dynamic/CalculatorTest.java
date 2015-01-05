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

        lb.newToken()
                .matchesString("(")
                .named("lparen")
                .nud((previous, parser, lexeme) -> {
                    CalculatorNode trailingExpression = parser.subExpression(previous);
                    parser.expectSingleLexeme(rparen.getKey());
                    return trailingExpression;
                }).build();

        lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        lb.newToken()
                .matchesString("+")
                .named("plus")
                .nud((previous, parser, lexeme) -> parser.subExpression(previous))
                .led((previous, parser, lexeme) -> new AdditionNode(previous, parser.subExpression(previous)))
                .build();

        lb.newToken()
                .matchesString("-")
                .named("minus")
                .nud((previous, parser, lexeme) -> new NegationNode(parser.subExpression(previous)))
                .led((previous, parser, lexeme) -> new SubtractionNode(previous, parser.subExpression(previous))).build();

        lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number").nud((previous, parser, lexeme) -> new NumberNode(Integer.parseInt(lexeme.getText()))).build();
        Language<CalculatorNode> l = lb.completeLanguage();
        assertEquals(3, l.newLexParser().tryParse("1+2").getRootNode().evaluate());
        assertEquals(-1, l.newLexParser().tryParse("1+-2").getRootNode().evaluate());
        assertEquals(3, l.newLexParser().tryParse("1--2").getRootNode().evaluate());
    }


    @Test
    public void testClearerSyntax() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();
        lb.newToken().named("rparen").matchesString(")").build();
        lb.newToken().named("lparen").matchesString("(").build();
        lb.newToken().named("whitespace").matchesPattern("\\s+").ignoreWhenParsing().build();
        TokenDefinitionBuilder<CalculatorNode> calculatorNodeTokenDefinitionBuilder2 = lb.newToken().named("plus").matchesString("+")
                .led((previous, parser, lexeme) -> new AdditionNode(previous, parser.subExpression(previous)));
        calculatorNodeTokenDefinitionBuilder2.build();
        TokenDefinitionBuilder<CalculatorNode> calculatorNodeTokenDefinitionBuilder1 = lb.newToken().named("minus").matchesString("-")
                .nud((previous, parser, lexeme) -> new NegationNode(parser.subExpression(previous)))
                .led((previous, parser, lexeme) -> new SubtractionNode(previous, parser.subExpression(previous)));
        calculatorNodeTokenDefinitionBuilder1.build();
        TokenDefinitionBuilder<CalculatorNode> calculatorNodeTokenDefinitionBuilder = lb.newToken().named("number").matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> new NumberNode(Integer.parseInt(lexeme.getText())));
        calculatorNodeTokenDefinitionBuilder.build();
        Language<CalculatorNode> l = lb.completeLanguage();

        assertEquals(3, l.newLexParser().tryParse("1+2").getRootNode().evaluate());
        assertEquals(-1, l.newLexParser().tryParse("1+-2").getRootNode().evaluate());
        assertEquals(3, l.newLexParser().tryParse("1--2").getRootNode().evaluate());
    }

    @Test
    public void testDirectCalculation() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        assertEquals(Integer.valueOf(7), l.newLexParser().tryParse("1 + 2 * 3").getRootNode());
    }

    @Test
    public void emptyInput() {
        Language<Integer> l = CalculatorTestHelper.getSimpleCalculatorLanguage();
        try {
            l.newLexParser().parse("");
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
