package com.torstling.tdop.fluid.minilang;


import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.fluid.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MiniLangTest {
    @Test
    public void test() {
        LanguageBuilder<LaiLaiNode> lb = new LanguageBuilder<>();

        final TokenDefinition<LaiLaiNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<LaiLaiNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix(new PrefixAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        LaiLaiNode trailingExpression = parser.expression();
                        parser.expectSingleToken(rparen);
                        return trailingExpression;
                    }
                }).build();

        TokenDefinition<LaiLaiNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<LaiLaiNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .supportsPrefix(new PrefixAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull LexingMatch match, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        return parser.expression();
                    }
                })
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        return new AdditionNode(left, parser.expression());
                    }
                })
                .build();
        TokenDefinition<LaiLaiNode> variable = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variable")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new VariableNode(match.getText());
                    }
                }).build();
        TokenDefinition<LaiLaiNode> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("variable")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new IntegerNode(Integer.parseInt(match.getText()));
                    }
                }).build();
        TokenDefinition<LaiLaiNode> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("variable")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new FloatNode(Float.parseFloat(match.getText()));
                    }
                }).build();
        Language<LaiLaiNode> l = lb
                .addToken(lparen)
                .addToken(rparen)
                .addToken(whitespace)
                .newLevel()
                .addToken(plus)
                .newLevel()
                .addToken(variable)
                .newLevel()
                .addToken(floatLiteral)
                .newLevel()
                .addToken(integerLiteral)
                .completeLanguage();
        String expr = "a=5; a+4";
        //assertEquals("", l.getLexer().lex(expr));
        //check(l, 3, expr);
    }

    private void check(Language<LaiLaiNode> l, Object expectedResult, String expression) {
        assertEquals(expectedResult, l.getParser().tryParse(expression).getNode().evaluate());
    }
}
