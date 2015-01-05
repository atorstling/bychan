package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.ParseResult;
import org.bychan.core.basic.ParsingFailedInformation;
import org.bychan.core.dynamic.DynamicNudParseAction;
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
        DynamicNudParseAction<BooleanExpressionNode> parseAction = (previous, parser, lexeme) -> new VariableNode(lexeme.getText());
        lb.newToken().matchesString("(").named("lparen").nud((previous, parser, lexeme) -> {
            BooleanExpressionNode trailingExpression = parser.expression(previous);
            parser.expectSingleLexeme(rparen.getKey());
            return trailingExpression;
        }).build();
        lb.newToken().matchesPattern("\\s+").named("whitespace").discardAfterLexing().build();
        lb.newToken().matchesString("!").named("not").nud((previous, parser, lexeme) -> new NotNode(parser.expression(previous))).build();
        lb.newToken().matchesString("&").named("and").led((previous, parser, lexeme) -> new AndNode(previous, parser.expression(previous))).build();
        lb.newToken().matchesPattern("[a-z]+").named("variable").nud(parseAction).build();
        Language<BooleanExpressionNode> l = lb.completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    @Test
    public void clearerSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.newToken().matchesString(")").named("rparen").build();
        lb.newToken().matchesString("(").named("lparen").nud((previous, parser, lexeme) -> {
            BooleanExpressionNode trailingExpression = parser.expression(previous);
            parser.expectSingleLexeme(rparen.getKey());
            return trailingExpression;
        }).build();
        lb.newToken().matchesPattern("\\s+").named("whitespace").discardAfterLexing().build();
        lb.newToken().matchesString("!").named("not").nud((previous, parser, lexeme) -> new NotNode(parser.expression(previous))).build();
        lb.newToken().matchesString("&").named("and").led((previous, parser, lexeme) -> new AndNode(previous, parser.expression(previous))).build();
        lb.newToken().matchesPattern("[a-z]+").named("variable").nud((previous, parser, lexeme) -> new VariableNode(lexeme.getText())).build();
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
        ParsingFailedInformation errorMessage = parseResult.getErrorMessage();
        assertEquals("Parsing failed: 'Expected token 'rparen', but got 'END'' @  position 1:2 (index 1), current lexeme is END and remaining lexemes are []", errorMessage.toString());
    }

    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.newLexParser().tryParse(expression);
        result.checkSuccess();
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}