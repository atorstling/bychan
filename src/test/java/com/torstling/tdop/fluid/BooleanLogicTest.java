package com.torstling.tdop.fluid;

import com.torstling.tdop.boolexp.*;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.ParseResult;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanLogicTest {

    @Test
    public void terserSyntax() {
        LanguageBuilder2<BooleanExpressionNode, Object> lb = new LanguageBuilder2<BooleanExpressionNode, Object>();
        final TokenDefinition<BooleanExpressionNode, Object> rparen = lb
                .newLevel().startToken().matchesString(")").named("rparen").completeTokenAndPause();
        Language<BooleanExpressionNode, Object> l = lb
                .newLevel().startToken().matchesString("(").named("lparen").supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode, Object>() {
                    @NotNull
                    public BooleanExpressionNode build(@NotNull Object parent, @NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode, Object> parser) {
                        BooleanExpressionNode trailingExpression = parser.expression(parent);
                        parser.expectSingleToken(rparen);
                        return trailingExpression;
                    }
                }).completeToken()
                .startToken().matchesPattern("\\s+").named("whitespace").ignoreWhenParsing().completeToken().endLevel()
                .newLevel()
                .startToken().matchesString("!").named("not").supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode, Object>() {
                    @NotNull
                    public BooleanExpressionNode build(@NotNull Object parent, @NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode, Object> parser) {
                        return new NotNode(parser.expression(parent));
                    }
                }).completeToken()
                .endLevel()
                .newLevel()
                .startToken().matchesString("&").named("and").supportsInfix(new InfixAstBuilder<BooleanExpressionNode, Object>() {
                    public BooleanExpressionNode build(@NotNull Object parent, @NotNull LexingMatch match, @NotNull BooleanExpressionNode left, @NotNull ParserCallback2<BooleanExpressionNode, Object> parser) {
                        return new AndNode(left, parser.expression(parent));
                    }
                }).completeToken()
                .endLevel()
                .newLevel()
                .startToken().matchesPattern("[a-z]+").named("variable").supportsStandalone(new StandaloneAstBuilder<BooleanExpressionNode, Object>() {
                    @NotNull
                    public BooleanExpressionNode build(@NotNull Object parent, @NotNull final LexingMatch match) {
                        return new VariableNode(match.getText());
                    }
                }).completeToken()
                .endLevel()
                .completeLanguage();
        checkParenthesisPrio(l);
        checkParseFailure(l);
    }

    @Test
    public void clearerSyntax() {
        LanguageBuilder<BooleanExpressionNode, Object> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode, Object> rparen = lb.newToken().matchesString(")").named("rparen").build();
        TokenDefinition<BooleanExpressionNode, Object> lparen = lb.newToken().matchesString("(").named("lparen").supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode, Object>() {
            @NotNull
            public BooleanExpressionNode build(@NotNull Object parent, @NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode, Object> parser) {
                BooleanExpressionNode trailingExpression = parser.expression(parent);
                parser.expectSingleToken(rparen);
                return trailingExpression;
            }
        }).build();
        TokenDefinition<BooleanExpressionNode, Object> whitespace = lb.newToken().matchesPattern("\\s+").named("whitespace").ignoredWhenParsing().build();
        TokenDefinition<BooleanExpressionNode, Object> not = lb.newToken().matchesString("!").named("not").supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode, Object>() {
            @NotNull
            public BooleanExpressionNode build(@NotNull Object parent, @NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode, Object> parser) {
                return new NotNode(parser.expression(parent));
            }
        }).build();
        TokenDefinition<BooleanExpressionNode, Object> and = lb.newToken().matchesString("&").named("and").supportsInfix(new InfixAstBuilder<BooleanExpressionNode, Object>() {
            public BooleanExpressionNode build(@NotNull Object parent, @NotNull LexingMatch match, @NotNull BooleanExpressionNode left, @NotNull ParserCallback2<BooleanExpressionNode, Object> parser) {
                return new AndNode(left, parser.expression(parent));
            }
        }).build();
        TokenDefinition<BooleanExpressionNode, Object> variable = lb.newToken().matchesPattern("[a-z]+").named("variable").supportsStandalone(new StandaloneAstBuilder<BooleanExpressionNode, Object>() {
            @NotNull
            public BooleanExpressionNode build(@NotNull Object parent, @NotNull final LexingMatch match) {
                return new VariableNode(match.getText());
            }
        }).build();
        Language<BooleanExpressionNode, Object> l = lb
                .newLowerPriorityLevel()
                .addToken(lparen).addToken(rparen).addToken(whitespace)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(not)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(and)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(variable)
                .endLevel()
                .completeLanguage();
        checkParenthesisPrio(l);
        checkParseFailure(l);
    }

    private void checkParenthesisPrio(@NotNull final Language<BooleanExpressionNode, Object> l) {
        check(l, "!( a & b) ", true, false, true);
        check(l, "!( a & b) ", true, true, false);
        check(l, "!  a & b  ", false, true, true);
    }

    private void checkParseFailure(@NotNull final Language<BooleanExpressionNode, Object> l) {
        ParseResult<BooleanExpressionNode> parseResult = l.getParser().tryParse(new BooleanExpressionRootNode(), "(a");
        Assert.assertTrue(parseResult.isFailure());
        String errorMessage = parseResult.getErrorMessage();
        assertEquals("Parsing terminated at lexing match LexingMatch{startPosition=2, endPosition=2, text='END'}: Expected a token of type 'rparen', but got 'END'", errorMessage);
    }

    private void check(@NotNull final Language<BooleanExpressionNode, Object> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.getParser().tryParse(new BooleanExpressionRootNode(), expression);
        assertTrue(result.isSuccess());
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}