package com.torstling.tdop.fluid;


import com.torstling.tdop.calculator.*;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    @Test
    public void test() {
        LanguageBuilder<CalculatorNode, CalculatorSymbolTable> lb = new LanguageBuilder<>();

        final TokenDefinition<CalculatorNode, CalculatorSymbolTable> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix(new PrefixAstBuilder<CalculatorNode, CalculatorSymbolTable>() {
                    @NotNull
                    public CalculatorNode build(@NotNull CalculatorSymbolTable parent, @NotNull LexingMatch match, @NotNull ParserCallback2<CalculatorNode, CalculatorSymbolTable> parser) {
                        CalculatorNode trailingExpression = parser.expression(parent);
                        parser.expectSingleToken(rparen);
                        return trailingExpression;
                    }
                }).build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .supportsPrefix(new PrefixAstBuilder<CalculatorNode, CalculatorSymbolTable>() {
                    @NotNull
                    public CalculatorNode build(@NotNull CalculatorSymbolTable parent, @NotNull LexingMatch match, @NotNull ParserCallback2<CalculatorNode, CalculatorSymbolTable> parser) {
                        return parser.expression(parent);
                    }
                })
                .supportsInfix(new InfixAstBuilder<CalculatorNode, CalculatorSymbolTable>() {
                    @Override
                    public CalculatorNode build(@NotNull CalculatorSymbolTable parent, @NotNull LexingMatch match, @NotNull CalculatorNode left, @NotNull ParserCallback2<CalculatorNode, CalculatorSymbolTable> parser) {
                        return new AdditionNode(left, parser.expression(parent));
                    }
                })
                .build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> minus = lb.newToken()
                .matchesString("-")
                .named("minus")
                .supportsPrefix(new PrefixAstBuilder<CalculatorNode, CalculatorSymbolTable>() {
                    @NotNull
                    @Override
                    public CalculatorNode build(@NotNull CalculatorSymbolTable parent, @NotNull LexingMatch match, @NotNull ParserCallback2<CalculatorNode, CalculatorSymbolTable> parser) {
                        return new NegationNode(parser.expression(parent));
                    }
                })
                .supportsInfix(new InfixAstBuilder<CalculatorNode, CalculatorSymbolTable>() {
                    public CalculatorNode build(@NotNull CalculatorSymbolTable parent, @NotNull LexingMatch match, @NotNull CalculatorNode left, @NotNull ParserCallback2<CalculatorNode, CalculatorSymbolTable> parser) {
                        return new SubtractionNode(left, parser.expression(parent));
                    }
                }).build();

        TokenDefinition<CalculatorNode, CalculatorSymbolTable> number = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("number")
                .supportsStandalone(new StandaloneAstBuilder<CalculatorNode, CalculatorSymbolTable>() {
                    @NotNull
                    public CalculatorNode build(@NotNull CalculatorSymbolTable parent, @NotNull final LexingMatch match) {
                        return new NumberNode(Integer.parseInt(match.getText()));
                    }
                }).build();
        Language<CalculatorNode, CalculatorSymbolTable> l = lb
                .addToken(lparen)
                .addToken(rparen)
                .addToken(whitespace)
                .newLowerPriorityLevel()
                .addToken(plus)
                .newLowerPriorityLevel()
                .addToken(minus)
                .newLowerPriorityLevel()
                .addToken(number)
                .completeLanguage();
        assertEquals(3, l.getParser().tryParse(new RootCalculatorNode(), "1+2").getRootNode().evaluate());
        assertEquals(-1, l.getParser().tryParse(new RootCalculatorNode(), "1+-2").getRootNode().evaluate());
        assertEquals(3, l.getParser().tryParse(new RootCalculatorNode(), "1--2").getRootNode().evaluate());
    }
}
