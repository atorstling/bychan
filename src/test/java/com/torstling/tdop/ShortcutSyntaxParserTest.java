package com.torstling.tdop;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

public class ShortcutSyntaxParserTest {


    @Test
    public void doit() {
        TokenDefinition<BooleanExpressionNode> not = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("!")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        return new NotNode(parser.expression());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> and = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("&")
                .supportsInfix(new InfixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull BooleanExpressionNode left, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        return new AndNode(left, parser.expression());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> variable = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("[a-z]+")
                .supportsStandalone(new StandaloneAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull final LexingMatch match) {
                        return new VariableNode(match.getText());
                    }
                }).build();
        final TokenDefinition<BooleanExpressionNode> rparen = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("(")
                .build();
        TokenDefinition<BooleanExpressionNode> lparen = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("(")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<BooleanExpressionNode> parser) {
                        BooleanExpressionNode trailingExpression = parser.expression();
                        parser.expect(rparen);
                        return trailingExpression;
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern(" *")
                .filterOutBeforeParsing()
                .build();
        Language l = new LanguageBuilder<BooleanExpressionNode>()
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
        ParseResult<BooleanExpressionNode> result = l.getParser().parse("!( a & b)");
    }
}
