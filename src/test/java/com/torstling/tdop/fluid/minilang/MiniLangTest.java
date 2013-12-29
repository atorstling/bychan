package com.torstling.tdop.fluid.minilang;


import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.fluid.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class MiniLangTest {
    @Test
    public void test() {
        LanguageBuilder<LaiLaiNode> lb = new LanguageBuilder<>();
        final HashMap<String, VariableNode> variables = new HashMap<>();

        final TokenDefinition<LaiLaiNode> rcurly = lb.newToken()
                .matchesString("}")
                .named("rcurly")
                .build();

        final TokenDefinition<LaiLaiNode> lcurly = lb.newToken()
                .matchesString("{")
                .named("lcurly")
                .supportsPrefix(new PrefixAstBuilder<LaiLaiNode>() {
                    @NotNull
                    @Override
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        ScopeNode scopeNode = new ScopeNode();
                        LaiLaiNode expression = parser.expression(scopeNode);
                        scopeNode.setChild(expression);
                        parser.expectSingleToken(rcurly);
                        return scopeNode;
                    }
                })
                .build();

        final TokenDefinition<LaiLaiNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<LaiLaiNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix(new PrefixAstBuilder<LaiLaiNode>() {
                    @NotNull
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        LaiLaiNode trailingExpression = parser.expression(parent);
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
                    @NotNull
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        return parser.expression(parent);
                    }
                })
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        return new AdditionNode(left, parser.expression(parent));
                    }
                })
                .build();

        TokenDefinition<LaiLaiNode> hat = lb.newToken()
                .matchesString("^")
                .named("hat")
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        ExpressionType actualExpressionType = left.getExpressionType();
                        if (ExpressionType.BOOL.equals(actualExpressionType)) {
                            return new XorNode(left, parser.expression(parent));
                        } else if (ExpressionType.FLOAT.equals(actualExpressionType)) {
                            return new PowNode(left, parser.expression(parent));
                        }
                        throw new IllegalStateException("'hat' only applicable to bool and float, got '" + left + "' of type '" + actualExpressionType + "'");
                    }
                })
                .build();

        TokenDefinition<LaiLaiNode> assign = lb.newToken()
                .matchesString("=")
                .named("assign")
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        LaiLaiNode right = parser.expression(parent);
                        return new AssignNode((VariableNode) left, right);
                    }
                })
                .build();

        TokenDefinition<LaiLaiNode> variableDeclaration = lb.newToken()
                .matchesPattern("(?:float|int|bool) [a-z]+")
                .named("variableDef")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    @NotNull
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        String declaration = match.getText();
                        Pattern variablePattern = Pattern.compile("^(float|int|bool) ([a-z]+)$");
                        Matcher matcher = variablePattern.matcher(declaration);
                        boolean matches = matcher.matches();
                        if (!matches) {
                            throw new IllegalStateException("No match for variable declaration'" + declaration + "'");
                        }
                        String typeDeclaration = matcher.group(1);
                        String nameDeclaration = matcher.group(2);

                        if (!variables.containsKey(nameDeclaration)) {
                            ExpressionType type = ExpressionType.forTypeDeclaration(typeDeclaration);
                            variables.put(nameDeclaration, new VariableNode(type, nameDeclaration));
                        }
                        return variables.get(nameDeclaration);
                    }
                }).build();

        TokenDefinition<LaiLaiNode> variableReference = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variableRef")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    @NotNull
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        String name = match.getText();
                        VariableNode variable = variables.get(name);
                        if (variable == null) {
                            throw new IllegalStateException("Variable '" + name + "' cannot be referenced, not yet defined.");
                        }
                        return variable;
                    }
                }).build();

        TokenDefinition<LaiLaiNode> booleanLiteral = lb.newToken()
                .matchesPattern("true|false")
                .named("bool")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    @NotNull
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new BooleanLiteralNode(Boolean.parseBoolean(match.getText()));
                    }
                }).build();

        TokenDefinition<LaiLaiNode> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+i")
                .named("int")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    @NotNull
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        String text = match.getText();
                        return new IntegerLiteralNode(Integer.parseInt(text.substring(0, text.length() - 1)));
                    }
                }).build();

        TokenDefinition<LaiLaiNode> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("float")
                .supportsStandalone(new StandaloneAstBuilder<LaiLaiNode>() {
                    @NotNull
                    public LaiLaiNode build(@NotNull final LexingMatch match) {
                        return new FloatLiteralNode(Float.parseFloat(match.getText()));
                    }
                }).build();

        TokenDefinition<LaiLaiNode> semicolon = lb.newToken()
                .matchesString(";")
                .named("statement")
                .supportsInfix(new InfixAstBuilder<LaiLaiNode>() {
                    @Override
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull LaiLaiNode left, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        return new StatementNode(left, parser.expression(parent));
                    }
                }).build();

        final TokenDefinition<LaiLaiNode> listEnd = lb.newToken()
                .matchesString("]")
                .named("listEnd")
                .build();

        final TokenDefinition<LaiLaiNode> comma = lb.newToken()
                .matchesString(",")
                .named("comma")
                .build();

        TokenDefinition<LaiLaiNode> listStart = lb.newToken()
                .matchesString("[")
                .named("listStart")
                .supportsPrefix(new PrefixAstBuilder<LaiLaiNode>() {
                    @NotNull
                    @Override
                    public LaiLaiNode build(@NotNull LaiLaiNode parent, @NotNull LexingMatch match, @NotNull ParserCallback2<LaiLaiNode> parser) {
                        ArrayList<LaiLaiNode> expressions = new ArrayList<>();
                        while (!parser.nextIs(listEnd)) {
                            expressions.add(parser.expression(parent));
                            if (!parser.nextIs(listEnd)) {
                                parser.expectSingleToken(comma);
                            }
                        }
                        parser.expectSingleToken(listEnd);
                        return new ListNode(expressions);
                    }
                }).build();

        Language<LaiLaiNode> l = lb
                .addToken(booleanLiteral)
                .newLowerPriorityLevel()
                .addToken(lcurly)
                .addToken(rcurly)
                .addToken(listEnd)
                .addToken(lparen)
                .addToken(rparen)
                .addToken(listStart)
                .addToken(comma)
                .addToken(whitespace)
                .addToken(integerLiteral)
                .addToken(floatLiteral)
                .addToken(variableDeclaration)
                .newLowerPriorityLevel()
                .addToken(variableReference)
                .newLowerPriorityLevel()
                .addToken(semicolon)
                .newLowerPriorityLevel()
                .addToken(assign)
                .newLowerPriorityLevel()
                .addToken(plus)
                .addToken(hat)
                .newLowerPriorityLevel()
                .completeLanguage();
        testOne(l);

        ParseResult<LaiLaiNode> r = l.getParser().tryParse(new MiniLangRootNode(), "{bool b=true;bool c=false;float d=2f;float e=4f;bool f=b^c;float g=d^e;[f,g]}");
        LaiLaiNode root = r.getRootNode();
        assertEquals("(s (x (x (x (x (x (x (= bool(b) true) (= bool(c) false)) (= float(d) 2.0f)) (= float(e) 4.0f)) (= bool(f) (xor bool(b) bool(c)))) (= float(g) (pow float(d) float(e)))) (l bool(f) float(g) )))", root.toString());
        assertEquals(Arrays.<Object>asList(Boolean.TRUE, 16f), root.evaluate());
    }

    private void testOne(Language<LaiLaiNode> l) {
        String expr = "int a=5i; a=a+4i; a";
        List<Token<LaiLaiNode>> tokens = l.getLexer().lex(expr);
        ParseResult<LaiLaiNode> result = l.getParser().tryParse(new MiniLangRootNode(), tokens);
        LaiLaiNode rootNode = result.getRootNode();
        assertEquals("(x (x (= int(a) 5i) (= int(a) (+ int(a) 4i))) int(a))", rootNode.toString());
        assertEquals(9, rootNode.evaluate());
    }
}
