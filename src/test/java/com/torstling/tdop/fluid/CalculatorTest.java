package com.torstling.tdop.fluid;


import com.torstling.tdop.calculator.*;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    @Test
    public void test() {
        LanguageBuilder<CalculatorNode> lb = new LanguageBuilder<>();

        final TokenDefinition<CalculatorNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<CalculatorNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix(new PrefixAstBuilder<CalculatorNode>() {
                    public CalculatorNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<CalculatorNode> parser) {
                        CalculatorNode trailingExpression = parser.expression();
                        parser.expectSingleToken(rparen);
                        return trailingExpression;
                    }
                }).build();

        TokenDefinition<CalculatorNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<CalculatorNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .supportsPrefix(new PrefixAstBuilder<CalculatorNode>() {
                    public CalculatorNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<CalculatorNode> parser) {
                        return parser.expression();
                    }
                })
                .supportsInfix(new InfixAstBuilder<CalculatorNode>() {
                    @Override
                    public CalculatorNode build(@NotNull LexingMatch match, @NotNull CalculatorNode left, @NotNull ParserCallback2<CalculatorNode> parser) {
                        return new AdditionNode(left, parser.expression());
                    }
                })
                .build();

        TokenDefinition<CalculatorNode> minus = lb.newToken()
                .matchesString("-")
                .named("minus")
                .supportsPrefix(new PrefixAstBuilder<CalculatorNode>() {
                    @Override
                    public CalculatorNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<CalculatorNode> parser) {
                        return new NegationNode(parser.expression());
                    }
                })
                .supportsInfix(new InfixAstBuilder<CalculatorNode>() {
                    public CalculatorNode build(@NotNull LexingMatch match, @NotNull CalculatorNode left, @NotNull ParserCallback2<CalculatorNode> parser) {
                        return new SubtractionNode(left, parser.expression());
                    }
                }).build();

        TokenDefinition<CalculatorNode> number = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number")
                .supportsStandalone(new StandaloneAstBuilder<CalculatorNode>() {
                    public CalculatorNode build(@NotNull final LexingMatch match) {
                        return new NumberNode(Integer.parseInt(match.getText()));
                    }
                }).build();
        Language<CalculatorNode> l = lb
                .addToken(lparen).addToken(rparen).addToken(whitespace)
                .newLevel()
                .addToken(plus)
                .newLevel()
                .addToken(minus)
                .newLevel()
                .addToken(number)
                .completeLanguage();
        assertEquals(3, l.getParser().tryParse("1+2").getNode().evaluate());
        assertEquals(-1, l.getParser().tryParse("1+-2").getNode().evaluate());
        assertEquals(3, l.getParser().tryParse("1--2").getNode().evaluate());
    }
}
