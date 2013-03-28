package com.torstling.tdop;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ShortcutSyntaxParserTest {


    @Test
    public void doit() {
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
        Language<BooleanExpressionNode> l = new LanguageBuilder<BooleanExpressionNode>()
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
        ParseResult<BooleanExpressionNode> result = l.getParser().parse("!( a & b) ");
        assertTrue(result.isSuccess());
        VariableBindings bindings = new VariableBindingBuilder().bind("a", false).bind("b", true).build();
        assertTrue(result.getRootNode().evaluate(bindings));
    }
}
