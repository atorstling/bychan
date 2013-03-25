package com.torstling.tdop;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ShortcutSyntaxParserTest {


    @Test
    public void doit() {
        TokenDefinition not = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("!")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull BooleanExpressionNode trailingExpression) {
                        return new NotNode(trailingExpression);
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> and = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("&")
                .supportsInfix(new InfixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull BooleanExpressionNode left, @NotNull BooleanExpressionNode right) {
                        return new AndNode(left, right);
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> variable = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesString("[a-z]+")
                .supportsStandalone(new StandaloneAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull final LexingMatch match) {
                        return new VariableNode(match.getText());
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = new TokenDefinitionBuilder<BooleanExpressionNode>()
                .matchesPattern(" *")
                .notParseable()
                .build();
        Language l = new LanguageBuilder()
                .addToken(whitespace)
                .addToken(not)
                .addToken(and)
                .addToken(variable)
                .build();
        l.getParser().parse("&!");
    }
}
