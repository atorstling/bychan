package com.torstling.tdop.fluid.minilang;


import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder;
import com.torstling.tdop.fluid.TokenDefinition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class MiniLangTest {
    @Test
    public void test() {
        LanguageBuilder<LaiLaiNode, LaiLaiSymbolTable> lb = new LanguageBuilder<>();

        final TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> rcurly = lb.newToken()
                .matchesString("}")
                .named("rcurly")
                .build();

        final TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> lcurly = lb.newToken()
                .matchesString("{")
                .named("lcurly")
                .supportsPrefix((parentSymbolTable, match, parser) -> {
                    NestedScope nestedScope = new NestedScope(parentSymbolTable);
                    LaiLaiNode expression = parser.expression(nestedScope);
                    parser.expectSingleToken(rcurly);
                    ScopeNode scopeNode = new ScopeNode(nestedScope);
                    scopeNode.setChild(expression);
                    return scopeNode;
                })
                .build();

        final TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .supportsPrefix((parent, match, parser) -> {
                    LaiLaiNode trailingExpression = parser.expression(parent);
                    parser.expectSingleToken(rparen);
                    return trailingExpression;
                }).build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .supportsPrefix((parent, match, parser) -> parser.expression(parent))
                .supportsInfix((parent, match, left, parser) -> new AdditionNode(left, parser.expression(parent)))
                .build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> hat = lb.newToken()
                .matchesString("^")
                .named("hat")
                .supportsInfix((symbolTable, match, left, parser) -> new HatNode(left, parser.expression(symbolTable)))
                .build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> assign = lb.newToken()
                .matchesString("=")
                .named("assign")
                .supportsInfix((parent, match, left, parser) -> {
                    LaiLaiNode right = parser.expression(parent);
                    return new AssignNode(left, right);
                })
                .build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> variableDeclaration = lb.newToken()
                .matchesPattern("(?:float|int|bool) [a-z]+")
                .named("variableDef")
                .supportsStandalone((parent, match) -> {
                    String declaration = match.getText();
                    Pattern variablePattern = Pattern.compile("^(float|int|bool) ([a-z]+)$");
                    Matcher matcher = variablePattern.matcher(declaration);
                    boolean matches = matcher.matches();
                    if (!matches) {
                        throw new IllegalStateException("No match for variable declaration'" + declaration + "'");

                    }
                    return new VariableDefNode(ExpressionType.forTypeDeclaration(matcher.group(1)), matcher.group(2));
                }).build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> variableReference = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variableRef")

                .supportsStandalone((symbolTable, match) -> {
                    String name = match.getText();
                    return new VariableRefNode(name);
                }).build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> booleanLiteral = lb.newToken()
                .matchesPattern("true|false")
                .named("bool")
                .supportsStandalone((parent, match) -> new BooleanLiteralNode(Boolean.parseBoolean(match.getText()))).build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+i")
                .named("int")
                .supportsStandalone((parent, match) -> {
                    String text = match.getText();
                    return new IntegerLiteralNode(Integer.parseInt(text.substring(0, text.length() - 1)));
                }).build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("float")
                .supportsStandalone((parent, match) -> new FloatLiteralNode(Float.parseFloat(match.getText()))).build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> semicolon = lb.newToken()
                .matchesString(";")
                .named("statement")
                .supportsInfix((parent, match, left, parser) -> new StatementNode(left, parser.expression(parent))).build();

        final TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> listEnd = lb.newToken()
                .matchesString("]")
                .named("listEnd")
                .build();

        final TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> comma = lb.newToken()
                .matchesString(",")
                .named("comma")
                .build();

        TokenDefinition<LaiLaiNode, LaiLaiSymbolTable> listStart = lb.newToken()
                .matchesString("[")
                .named("listStart")
                .supportsPrefix((parent, match, parser) -> {
                    ArrayList<LaiLaiNode> expressions = new ArrayList<>();
                    while (parser.nextIsNot(listEnd)) {
                        expressions.add(parser.expression(parent));
                        if (parser.nextIsNot(listEnd)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(listEnd);
                    return new ListNode(expressions);
                }).build();

        Language<LaiLaiNode, LaiLaiSymbolTable> l = lb
                .newLowerPriorityLevel()
                .addToken(booleanLiteral)
                .endLevel()
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
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(variableReference)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(semicolon)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(assign)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(plus)
                .addToken(hat)
                .endLevel()
                .completeLanguage();
        testOne(l);

        testTwo(l);

        ParseResult<LaiLaiNode> r = l.getParser().tryParse(new MiniLangRootNode(), "{int a=1i; int b=2i; { int a=3i; a+b}}");
        assertEquals(5, r.getRootNode().evaluate(null));
    }

    private void testTwo(Language<LaiLaiNode, LaiLaiSymbolTable> l) {
        ParseResult<LaiLaiNode> r = l.getParser().tryParse(new MiniLangRootNode(), "{bool b=true;bool c=false;float d=2f;float e=4f;bool f=b^c;float g=d^e;[f,g]}");
        LaiLaiNode root = r.getRootNode();
        assertEquals("(s (x (x (x (x (x (x (= bool(b) true) (= bool(c) false)) (= float(d) 2.0f)) (= float(e) 4.0f)) (= bool(f) (^ b c))) (= float(g) (^ d e))) (l f g )))", root.toString());
        assertEquals(Arrays.<Object>asList(Boolean.TRUE, 16f), root.evaluate(null));
    }

    private void testOne(Language<LaiLaiNode, LaiLaiSymbolTable> l) {
        String expr = "{int a=5i; a=a+4i; a}";
        List<Token<LaiLaiNode, LaiLaiSymbolTable>> tokens = l.getLexer().lex(expr);
        ParseResult<LaiLaiNode> result = l.getParser().tryParse(new MiniLangRootNode(), tokens);
        LaiLaiNode rootNode = result.getRootNode();
        assertEquals("(s (x (x (= int(a) 5i) (= a (+ a 4i))) a))", rootNode.toString());
        assertEquals(9, rootNode.evaluate(null));
    }
}
