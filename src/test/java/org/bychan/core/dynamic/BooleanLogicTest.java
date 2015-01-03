package org.bychan.core.dynamic;

import org.bychan.core.basic.ParseResult;
import org.bychan.core.basic.ParsingFailedInformation;
import org.bychan.core.langs.boolexp.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BooleanLogicTest {

    @Test
    public void terserSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.startToken().matchesString(")").named("rparen").buildAndAdd();
        DynamicPrefixParseAction<BooleanExpressionNode> parseAction = (previous, match, parser, lbp) -> new VariableNode(match.getText());
        Language<BooleanExpressionNode> l = lb.startToken().matchesString("(").named("lparen").prefixParseAs((previous, match, parser, lbp) -> {
            BooleanExpressionNode trailingExpression = parser.subExpression();
            parser.expectSingleLexeme(rparen.getKey());
            return trailingExpression;
        }).end()
                .startToken().matchesPattern("\\s+").named("whitespace").ignoredWhenParsing().end()
                .startToken().matchesString("!").named("not").prefixParseAs((previous, match, parser, lbp) -> new NotNode(parser.subExpression())).end()
                .startToken().matchesString("&").named("and").infixParseAs((previous, match, parser, lbp) -> new AndNode(previous, parser.subExpression())).end()
                .startToken().matchesPattern("[a-z]+").named("variable").prefixParseAs(parseAction).end()
                .completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    @Test
    public void clearerSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.newToken().matchesString(")").named("rparen").build();
        TokenDefinition<BooleanExpressionNode> lparen = lb.newToken().matchesString("(").named("lparen").prefixParseAs((previous, match, parser, lbp) -> {
            BooleanExpressionNode trailingExpression = parser.subExpression();
            parser.expectSingleLexeme(rparen.getKey());
            return trailingExpression;
        }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = lb.newToken().matchesPattern("\\s+").named("whitespace").ignoredWhenParsing().build();
        TokenDefinition<BooleanExpressionNode> not = lb.newToken().matchesString("!").named("not").prefixParseAs((previous, match, parser, lbp) -> new NotNode(parser.subExpression())).build();
        TokenDefinition<BooleanExpressionNode> and = lb.newToken().matchesString("&").named("and").infixParseAs((previous, match, parser, lbp) -> new AndNode(previous, parser.subExpression())).build();
        TokenDefinition<BooleanExpressionNode> variable = lb.newToken().matchesPattern("[a-z]+").named("variable").prefixParseAs((previous, match, parser, lbp) -> new VariableNode(match.getText())).build();
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
        assertEquals("Parsing failed: 'Expected token 'rparen', but got 'END'' @  position 1:2 (index 1), current lexeme is END and remaining lexemes are []", errorMessage.toString());
    }

    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.getLexParser().tryParse(expression);
        result.checkSuccess();
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}