package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.LexParsingFailedInformation;
import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinition;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BooleanLogicTest {

    @Test
    public void terserSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.newToken().matchesString(")").named("rparen").build();
        lb.newToken().matchesString("(").named("lparen").nud((left, parser, lexeme) -> {
            BooleanExpressionNode trailingExpression = parser.expression(left);
            parser.expectSingleLexeme(rparen.getKey());
            return trailingExpression;
        }).build();
        lb.powerUp();
        lb.newToken().matchesPattern("\\s+").named("whitespace").discardAfterLexing().build();
        lb.newToken().matchesString("!").named("not").nud((left, parser, lexeme) -> new NotNode(parser.expression(left))).build();
        lb.newToken().matchesString("&").named("and").led((left, parser, lexeme) -> new AndNode(left, parser.expression(left))).build();
        lb.newToken().matchesPattern("[a-z]+").named("variable").nud((left, parser, lexeme) -> new VariableNode(lexeme.getText())).build();
        Language<BooleanExpressionNode> l = lb.completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    @Test
    public void clearerSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.newToken().matchesString(")").named("rparen").build();
        lb.newToken().matchesString("(").named("lparen").nud((left, parser, lexeme) -> {
            BooleanExpressionNode trailingExpression = parser.expression(left);
            parser.expectSingleLexeme(rparen.getKey());
            return trailingExpression;
        }).build();
        lb.powerUp();
        lb.newToken().matchesPattern("\\s+").named("whitespace").discardAfterLexing().build();
        lb.newToken().matchesString("!").named("not").nud((left, parser, lexeme) -> new NotNode(parser.expression(left))).build();
        lb.newToken().matchesString("&").named("and").led((left, parser, lexeme) -> new AndNode(left, parser.expression(left))).build();
        lb.newToken().matchesPattern("[a-z]+").named("variable").nud((left, parser, lexeme) -> new VariableNode(lexeme.getText())).build();
        Language<BooleanExpressionNode> l = lb.completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    private void checkparanthesisPrio(@NotNull final Language<BooleanExpressionNode> l) {
        check(l, "!( a & b) ", true, false, true);
        check(l, "!( a & b) ", true, true, false);
        check(l, "!  a & b  ", false, true, true);
    }

    private void checkParseFailure(@NotNull final Language<BooleanExpressionNode> l) {
        ParseResult<BooleanExpressionNode> parseResult = l.newLexParser().tryParse("(a");
        Assert.assertTrue(parseResult.isFailure());
        LexParsingFailedInformation errorMessage = parseResult.getErrorMessage();
        assertEquals("Parsing failed: 'Expected token 'rparen', but got 'END'' @  position 1:2 (index 1), current lexeme is END, previous was variable(a), and remaining are []", errorMessage.toString());
    }

    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.newLexParser().tryParse(expression);
        result.checkSuccess();
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}