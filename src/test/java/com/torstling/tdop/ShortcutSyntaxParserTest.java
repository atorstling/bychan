package com.torstling.tdop;

import com.sun.istack.internal.NotNull;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShortcutSyntaxParserTest {

    private Language<BooleanExpressionNode> l;

    @Before
    public void setupLanguage() {
        TokenDefinition<BooleanExpressionNode> not = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("\\!")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        return new NotNode(parser.expression());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> and = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("\\&")
                .supportsInfix(new InfixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull BooleanExpressionNode left, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        return new AndNode(left, parser.expression());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> variable = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("[a-z]+")
                .supportsStandalone(new StandaloneAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull final LexingMatch match) {
                        return new VariableNode(match.getText());
                    }
                }).build();
        final TokenDefinition<BooleanExpressionNode> rparen = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("\\)")
                .build();
        TokenDefinition<BooleanExpressionNode> lparen = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("\\(")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        BooleanExpressionNode trailingExpression = parser.expression();
                        parser.expect(rparen);
                        return trailingExpression;
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern("\\s+")
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
    public void paranthesisPrio() {
        check(l, "!( a & b) ", true, false, true);
        check(l, "!  a & b  ", false, true, true);
    }

    @Test
    public void parseFailure() {
        //ParseResult<BooleanExpressionNode> parseResult = l.getParser().parse("(a");
        //Assert.assertTrue(parseResult.isFailure());
    }

    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.getParser().parse(expression);
        assertTrue(result.isSuccess());
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}
