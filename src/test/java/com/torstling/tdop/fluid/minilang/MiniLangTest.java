package com.torstling.tdop.fluid.minilang;


import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.fluid.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MiniLangTest {
    @Test
    public void test() {
        LanguageBuilder<LaiLaiNode> lb = new LanguageBuilder<>();
        final HashMap<String, VariableNode> variables = new HashMap<>();

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

        TokenDefinition<LaiLaiNode> assign = lb.newToken()
                .matchesString("=")
                .named("assign")
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        LaiLaiNode right = parser.expression();
                        return new AssignNode((VariableNode) left, right);
                    }
                })
                .build();

        TokenDefinition<LaiLaiNode> variable = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variable")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        String name = match.getText();
                        if (!variables.containsKey(name)) {
                            variables.put(name, new VariableNode(name));
                        }
                        return variables.get(name);
                    }
                }).build();

        TokenDefinition<LaiLaiNode> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+")
                .named("int")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new IntegerLiteralNode(Integer.parseInt(match.getText()));
                    }
                }).build();

        TokenDefinition<LaiLaiNode> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("float")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new FloatLiteralNode(Float.parseFloat(match.getText()));
                    }
                }).build();

        TokenDefinition<LaiLaiNode> semicolon = lb.newToken()
                .matchesString(";")
                .named("statement")
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        return new StatementNode(left, parser.expression());
                    }
                }).build();

        Language<LaiLaiNode> l = lb
                .addToken(lparen)
                .addToken(rparen)
                .addToken(whitespace)
                .addToken(variable)
                .addToken(integerLiteral)
                .addToken(floatLiteral)
                .newLowerPriorityLevel()
                .addToken(semicolon)
                .newLowerPriorityLevel()
                .addToken(assign)
                .newLowerPriorityLevel()
                .addToken(plus)
                .completeLanguage();
        String expr = "a=5; a=a+4; a";
        List<Token<LaiLaiNode>> tokens = l.getLexer().lex(expr);
        ParseResult<LaiLaiNode> result = l.getParser().tryParse(tokens);
        LaiLaiNode rootNode = result.getRootNode();
        assertEquals("(x (x (= a 5i) (= a (+ a 4i))) a)", rootNode.toString());
        assertEquals(9, rootNode.evaluate());
    }
}
