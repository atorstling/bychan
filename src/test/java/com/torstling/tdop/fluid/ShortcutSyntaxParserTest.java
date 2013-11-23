package com.torstling.tdop.fluid;

import com.torstling.tdop.boolexp.AndNode;
import com.torstling.tdop.boolexp.BooleanExpressionNode;
import com.torstling.tdop.boolexp.NotNode;
import com.torstling.tdop.boolexp.VariableNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.ParseResult;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShortcutSyntaxParserTest {

    private Language<BooleanExpressionNode> l;

    @Before
    public void setupLanguage() {
        TokenDefinition<BooleanExpressionNode> not = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("!")
                .named("not")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        return new NotNode(parser.expression());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> and = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("&")
                .named("and")
                .supportsInfix(new InfixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull BooleanExpressionNode left, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        return new AndNode(left, parser.expression());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> variable = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("[a-z]+")
                .named("variable")
                .supportsStandalone(new StandaloneAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull final LexingMatch match) {
                        return new VariableNode(match.getText());
                    }
                }).build();
        final TokenDefinition<BooleanExpressionNode> rparen = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString(")")
                .named("rparen")
                .build();
        TokenDefinition<BooleanExpressionNode> lparen = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        BooleanExpressionNode trailingExpression = parser.expression();
                        parser.singleToken(rparen);
                        return trailingExpression;
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("\\s+")
                .named("whitespace")
                .filterOutBeforeParsing()
                .build();
        l = new LanguageBuilder<BooleanExpressionNode>()
                .addToken(whitespace)
                .addToken(lparen)
                .addToken(rparen)
                .newLevel()
                .addToken(not)
                .newLevel()
                .addToken(and)
                .newLevel()
                .addToken(variable)
                .build();
    }


    @Test
    public void parenthesisPrio() {
        check(l, "!( a & b) ", true, false, true);
        check(l, "!( a & b) ", true, true, false);
        check(l, "!  a & b  ", false, true, true);
    }


    @Test
    public void parseFailure() {
        ParseResult<BooleanExpressionNode> parseResult = l.getParser().tryParse("(a");
        Assert.assertTrue(parseResult.isFailure());
        String errorMessage = parseResult.getErrorMessage();
        assertEquals("Parsing terminated at position 2: Expected a token of type 'rparen', but got '.'", errorMessage);
    }


    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.getParser().tryParse(expression);
        assertTrue(result.isSuccess());
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getNode().evaluate(bindings), expectedOutcome);
    }
}
