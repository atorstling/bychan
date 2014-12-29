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
        int first = 1;
        int second = 2;
        int third = 3;
        int fourth = 4;
        int fifth = 5;

        LanguageBuilder<LaiLaiNode> lb = new LanguageBuilder<>();

        final TokenDefinitionBuilder<LaiLaiNode> rcurly = lb.newToken()
                .matchesString("}")
                .named("rcurly");

        final TokenDefinitionBuilder<LaiLaiNode> lcurly = lb.newToken()
                .matchesString("{")
                .named("lcurly")
                .prefixParseAs((previous, match, parser) -> {
                    Scope scope = (previous == null) ? new RootScope() : previous.getScope() == null ? new RootScope() : new NestedScope(previous.getScope());
                    ScopeNode scopeNode = new ScopeNode(scope);
                    LaiLaiNode expression = parser.subExpression(scopeNode);
                    scopeNode.setChild(expression);
                    parser.expectSingleToken(rcurly.getKey());
                    return scopeNode;
                });

        final TokenDefinitionBuilder<LaiLaiNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen");

        TokenDefinitionBuilder<LaiLaiNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .prefixParseAs((previous, match, parser) -> {
                    LaiLaiNode trailingExpression = parser.subExpression();
                    parser.expectSingleToken(rparen.getKey());
                    return trailingExpression;
                });

        TokenDefinitionBuilder<LaiLaiNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing();

        TokenDefinitionBuilder<LaiLaiNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .prefixParseAs((previous, match, parser) -> parser.subExpression())
                .infixParseAs((previous, match, parser) -> new AdditionNode(previous, parser.subExpression()));

        TokenDefinitionBuilder<LaiLaiNode> hat = lb.newToken()
                .matchesString("^")
                .named("hat")
                .infixParseAs((previous, match, parser) -> new HatNode(previous, parser.subExpression()));

        TokenDefinitionBuilder<LaiLaiNode> assign = lb.newToken()
                .matchesString("=")
                .named("assign")
                .infixParseAs((previous, match, parser) -> {
                    LaiLaiNode right = parser.subExpression();
                    return new AssignNode(previous, right);
                });

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
        TokenDefinitionBuilder<LaiLaiNode> variableDeclaration = lb.newToken()
                .matchesPattern("(?:float|int|bool) [a-z]+")
                .named("variableDef").prefixParseAs(parseAction4);

        TokenDefinitionBuilder<LaiLaiNode> variableReference = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variableRef").prefixParseAs((previous, match, parser) -> {
                    String name = match.getText();
                    return new VariableRefNode(name);
                });

        TokenDefinitionBuilder<LaiLaiNode> booleanLiteral = lb.newToken()
                .matchesPattern("true|false")
                .named("bool").prefixParseAs((previous, match, parser) -> new BooleanLiteralNode(Boolean.parseBoolean(match.getText())));

        TokenDefinitionBuilder<LaiLaiNode> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+i")
                .named("int").prefixParseAs((previous, match, parser) -> {
                    String text = match.getText();
                    return new IntegerLiteralNode(previous, Integer.parseInt(text.substring(0, text.length() - 1)));
                });

        TokenDefinitionBuilder<LaiLaiNode> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("float").prefixParseAs((previous, match, parser) -> new FloatLiteralNode(previous, Float.parseFloat(match.getText())));

        TokenDefinitionBuilder<LaiLaiNode> semicolon = lb.newToken()
                .matchesString(";")
                .named("statement")
                .infixParseAs((previous, match, parser) -> new StatementNode(previous, parser.subExpression()));

        final TokenDefinitionBuilder<LaiLaiNode> listEnd = lb.newToken()
                .matchesString("]")
                .named("listEnd");

        final TokenDefinitionBuilder<LaiLaiNode> comma = lb.newToken()
                .matchesString(",")
                .named("comma");

        TokenDefinitionBuilder<LaiLaiNode> listStart = lb.newToken()
                .matchesString("[")
                .named("listStart")
                .prefixParseAs((previous, match, parser) -> {
                    ArrayList<LaiLaiNode> expressions = new ArrayList<>();
                    while (!parser.nextIs(listEnd.getKey())) {
                        expressions.add(parser.subExpression());
                        if (!parser.nextIs(listEnd.getKey())) {
                            parser.expectSingleToken(comma.getKey());
                        }
                    }
                    parser.expectSingleToken(listEnd.getKey());
                    return new ListNode(previous, expressions);
                });

        Language<LaiLaiNode> l = lb
                .addToken(booleanLiteral.leftBindingPower(first).build())
                .addToken(lcurly.leftBindingPower(first).build())
                .addToken(rcurly.leftBindingPower(first).build())
                .addToken(listEnd.leftBindingPower(first).build())
                .addToken(lparen.leftBindingPower(first).build())
                .addToken(rparen.leftBindingPower(first).build())
                .addToken(listStart.leftBindingPower(first).build())
                .addToken(comma.leftBindingPower(first).build())
                .addToken(whitespace.leftBindingPower(first).build())
                .addToken(integerLiteral.leftBindingPower(first).build())
                .addToken(floatLiteral.leftBindingPower(first).build())
                .addToken(variableDeclaration.leftBindingPower(first).build())
                .addToken(variableReference.leftBindingPower(second).build())
                .addToken(semicolon.leftBindingPower(third).build())
                .addToken(assign.leftBindingPower(fourth).build())
                .addToken(plus.leftBindingPower(fifth).build())
                .addToken(hat.leftBindingPower(fifth).build())
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
