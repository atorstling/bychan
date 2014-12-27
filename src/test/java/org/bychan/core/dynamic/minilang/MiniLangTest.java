package org.bychan.core.dynamic.minilang;


import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class MiniLangTest {
    @Test
    public void test() {
        int first=1;
        int second=2;
        int third=3;
        int fourth=4;
        int fifth=5;

        LanguageBuilder<LaiLaiNode> lb = new LanguageBuilder<>();

        final TokenDefinition<LaiLaiNode> rcurly = lb.newToken()
                .matchesString("}")
                .leftBindingPower(first)
                .named("rcurly")
                .build();

        final TokenDefinition<LaiLaiNode> lcurly = lb.newToken()
                .matchesString("{")
                .leftBindingPower(first)
                .named("lcurly")
                .prefixParseAs((previous, match, parser) -> {
                    Scope scope = (previous == null) ? new RootScope() : previous.getScope() == null ? new RootScope() : new NestedScope(previous.getScope());
                    ScopeNode scopeNode = new ScopeNode(scope);
                    LaiLaiNode expression = parser.subExpression(scopeNode);
                    scopeNode.setChild(expression);
                    parser.expectSingleToken(rcurly);
                    return scopeNode;
                })
                .build();

        final TokenDefinition<LaiLaiNode> rparen = lb.newToken()
                .leftBindingPower(first)
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<LaiLaiNode> lparen = lb.newToken()
                .leftBindingPower(first)
                .matchesString("(")
                .named("lparen")
                .prefixParseAs((previous, match, parser) -> {
                    LaiLaiNode trailingExpression = parser.subExpression();
                    parser.expectSingleToken(rparen);
                    return trailingExpression;
                }).build();

        TokenDefinition<LaiLaiNode> whitespace = lb.newToken()
                .leftBindingPower(first)
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<LaiLaiNode> plus = lb.newToken()
                .leftBindingPower(fifth)
                .matchesString("+")
                .named("plus")
                .prefixParseAs((previous, match, parser) -> parser.subExpression())
                .infixParseAs((previous, match, parser) -> new AdditionNode(previous, parser.subExpression()))
                .build();

        TokenDefinition<LaiLaiNode> hat = lb.newToken()
                .leftBindingPower(fifth)
                .matchesString("^")
                .named("hat")
                .infixParseAs((previous, match, parser) -> new HatNode(previous, parser.subExpression()))
                .build();

        TokenDefinition<LaiLaiNode> assign = lb.newToken()
                .leftBindingPower(fourth)
                .matchesString("=")
                .named("assign")
                .infixParseAs((previous, match, parser) -> {
                    LaiLaiNode right = parser.subExpression();
                    return new AssignNode(previous, right);
                })
                .build();

        DynamicPrefixParseAction<LaiLaiNode> parseAction4 = (previous, match, parser) -> {
            String declaration = match.getText();
            Pattern variablePattern = Pattern.compile("^(float|int|bool) ([a-z]+)$");
            Matcher matcher = variablePattern.matcher(declaration);
            boolean matches = matcher.matches();
            if (!matches) {
                throw new IllegalStateException("No match for variable declaration'" + declaration + "'");

            }
            return new VariableDefNode(previous, ExpressionType.forTypeDeclaration(matcher.group(1)), matcher.group(2));
        };
        TokenDefinition<LaiLaiNode> variableDeclaration = lb.newToken()
                .leftBindingPower(first)
                .matchesPattern("(?:float|int|bool) [a-z]+")
                .named("variableDef").prefixParseAs(parseAction4).build();

        TokenDefinition<LaiLaiNode> variableReference = lb.newToken()
                .leftBindingPower(second)
                .matchesPattern("[a-z]+")
                .named("variableRef").prefixParseAs((previous, match, parser) -> {
                    String name = match.getText();
                    return new VariableRefNode(name);
                }).build();

        TokenDefinition<LaiLaiNode> booleanLiteral = lb.newToken()
                .leftBindingPower(first)
                .matchesPattern("true|false")
                .named("bool").prefixParseAs((previous, match, parser) -> new BooleanLiteralNode(Boolean.parseBoolean(match.getText()))).build();

        TokenDefinition<LaiLaiNode> integerLiteral = lb.newToken()
                .leftBindingPower(first)
                .matchesPattern("[0-9]+i")
                .named("int").prefixParseAs((previous, match, parser) -> {
                    String text = match.getText();
                    return new IntegerLiteralNode(previous, Integer.parseInt(text.substring(0, text.length() - 1)));
                }).build();

        TokenDefinition<LaiLaiNode> floatLiteral = lb.newToken()
                .leftBindingPower(first)
                .matchesPattern("[0-9]+f")
                .named("float").prefixParseAs((previous, match, parser) -> new FloatLiteralNode(previous, Float.parseFloat(match.getText()))).build();

        TokenDefinition<LaiLaiNode> semicolon = lb.newToken()
                .leftBindingPower(third)
                .matchesString(";")
                .named("statement")
                .infixParseAs((previous, match, parser) -> new StatementNode(previous, parser.subExpression())).build();

        final TokenDefinition<LaiLaiNode> listEnd = lb.newToken()
                .leftBindingPower(first)
                .matchesString("]")
                .named("listEnd")
                .build();

        final TokenDefinition<LaiLaiNode> comma = lb.newToken()
                .leftBindingPower(first)
                .matchesString(",")
                .named("comma")
                .build();

        TokenDefinition<LaiLaiNode> listStart = lb.newToken()
                .leftBindingPower(first)
                .matchesString("[")
                .named("listStart")
                .prefixParseAs((previous, match, parser) -> {
                    ArrayList<LaiLaiNode> expressions = new ArrayList<>();
                    while (!parser.nextIs(listEnd)) {
                        expressions.add(parser.subExpression());
                        if (!parser.nextIs(listEnd)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(listEnd);
                    return new ListNode(previous, expressions);
                }).build();

        Language<LaiLaiNode> l = lb
                .addToken(booleanLiteral)
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
                .addToken(variableReference)
                .addToken(semicolon)
                .addToken(assign)
                .addToken(plus)
                .addToken(hat)
                .completeLanguage();
        testOne(l);

        testTwo(l);

        ParseResult<LaiLaiNode> r = l.getLexParser().tryParse("{int a=1i; int b=2i; { int a=3i; a+b}}");
        assertEquals(5, r.getRootNode().evaluate(null));
    }

    private void testTwo(Language<LaiLaiNode> l) {
        ParseResult<LaiLaiNode> r = l.getLexParser().tryParse("{bool b=true;bool c=false;float d=2f;float e=4f;bool f=b^c;float g=d^e;[f,g]}");
        LaiLaiNode root = r.getRootNode();
        assertEquals("(s (x (x (x (x (x (x (= bool(b) true) (= bool(c) false)) (= float(d) 2.0f)) (= float(e) 4.0f)) (= bool(f) (^ b c))) (= float(g) (^ d e))) (l f g )))", root.toString());
        assertEquals(Arrays.asList(Boolean.TRUE, 16f), root.evaluate(null));
    }

    private void testOne(Language<LaiLaiNode> l) {
        String expr = "{int a=5i; a=a+4i; a}";
        ParseResult<LaiLaiNode> result = l.getLexParser().tryParse(expr);
        LaiLaiNode rootNode = result.getRootNode();
        assertEquals("(s (x (x (= int(a) 5i) (= a (+ a 4i))) a))", rootNode.toString());
        assertEquals(9, rootNode.evaluate(null));
    }
}
