package com.torstling.tdop;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShortcutSyntaxParserTest {


    @Test
    public void doit() {

        TokenDefinition not = new TokenDefinitionBuilder<BooleanExpressionNode>("!")
                .supportsPrefix(new PrefixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull BooleanExpressionNode trailingExpression) {
                        return new NotNode(trailingExpression);
                    }
                }).build();
        TokenDefinition<BooleanExpressionNode> and = new TokenDefinitionBuilder<BooleanExpressionNode>("&")
                .supportsInfix(new InfixAstBuilder<BooleanExpressionNode>() {
                    public BooleanExpressionNode build(@NotNull BooleanExpressionNode left, @NotNull BooleanExpressionNode right) {
                        return new AndNode(left, right);
                    }
                }).build();
        LanguageBuilder builder = new LanguageBuilder();
        Language l = builder.addToken(not).addToken(and).build();
        l.getParser().parse("&!");
    }

    private BooleanExpressionNode parse(String expression) {
        BooleanExpressionParser parser = new BooleanExpressionParser();
        return parser.parse(expression);
    }

    private void checkTruth(BooleanExpressionNode root, boolean aValue, boolean bValue, boolean expectedOutcome) {
        assertEquals(expectedOutcome, root.evaluate(new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build()));
    }
}
