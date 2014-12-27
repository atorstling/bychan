package org.bychan.fluid;

import org.bychan.boolexp.*;
import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BooleanLogicTest {

    @Test
    public void terserSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.startToken().matchesString(")").named("rparen").completeTokenAndPause();
        //Very irky syntax. A level doesn't get registered if it's never ended. Consider looking for incomplete levels.
        Language<BooleanExpressionNode> l = lb.startToken().matchesString("(").named("lparen").prefixParseAs((previous, match, parser) -> {
            BooleanExpressionNode trailingExpression = parser.subExpression();
            parser.expectSingleToken(rparen);
            return trailingExpression;
        }).completeToken()
                .startToken().matchesPattern("\\s+").named("whitespace").ignoredWhenParsing().completeToken()
                .startToken().matchesString("!").named("not").prefixParseAs((previous, match, parser) -> new NotNode(parser.subExpression())).completeToken()
                .startToken().matchesString("&").named("and").infixParseAs((match, previous, parser) -> new AndNode(previous, parser.subExpression())).completeToken()
                .startToken().matchesPattern("[a-z]+").named("variable").standaloneParseAs((previous, match) -> new VariableNode(match.getText())).completeToken()
                .completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    @Test
    public void clearerSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.newToken().matchesString(")").named("rparen").build();
        TokenDefinition<BooleanExpressionNode> lparen = lb.newToken().matchesString("(").named("lparen").prefixParseAs((previous, match, parser) -> {
            BooleanExpressionNode trailingExpression = parser.subExpression();
            parser.expectSingleToken(rparen);
            return trailingExpression;
        }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = lb.newToken().matchesPattern("\\s+").named("whitespace").ignoredWhenParsing().build();
        TokenDefinition<BooleanExpressionNode> not = lb.newToken().matchesString("!").named("not").prefixParseAs((previous, match, parser) -> new NotNode(parser.subExpression())).build();
        TokenDefinition<BooleanExpressionNode> and = lb.newToken().matchesString("&").named("and").infixParseAs((match, previous, parser) -> new AndNode(previous, parser.subExpression())).build();
        TokenDefinition<BooleanExpressionNode> variable = lb.newToken().matchesPattern("[a-z]+").named("variable").standaloneParseAs((previous, match) -> new VariableNode(match.getText())).build();
        Language<BooleanExpressionNode> l = lb
                .addToken(lparen).addToken(rparen).addToken(whitespace)
                .addToken(not)
                .addToken(and)
                .addToken(variable)
                .completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    private void checkparanthesisPrio(@NotNull final Language<BooleanExpressionNode> l) {
        check(l, "!( a & b) ", true, false, true);
        check(l, "!( a & b) ", true, true, false);
        check(l, "!  a & b  ", false, true, true);
    }

    private void checkParseFailure(@NotNull final Language<BooleanExpressionNode> l) {
        ParseResult<BooleanExpressionNode> parseResult = l.getLexParser().tryParse("(a");
        Assert.assertTrue(parseResult.isFailure());
        ParsingFailedInformation errorMessage = parseResult.getErrorMessage();
        assertEquals("Parsing failed: 'Expected a token of type 'rparen', but got 'END'' @  index 2, current token is END and remaining tokens are []", errorMessage.toString());
    }

    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.getLexParser().tryParse(expression);
        result.checkSuccess();
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}