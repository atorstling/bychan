package org.bychan.core.langs.minilang;


import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinitionBuilder;
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
                .nud((left, parser, lexeme) -> {
                    Scope scope = (left == null) ? new RootScope() : left.getScope() == null ? new RootScope() : new NestedScope(left.getScope());
                    ScopeNode scopeNode = new ScopeNode(scope);
                    LaiLaiNode expression = parser.expression(scopeNode);
                    scopeNode.setChild(expression);
                    parser.expectSingleLexeme(rcurly.getKey());
                    return scopeNode;
                });

        final TokenDefinitionBuilder<LaiLaiNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen");

        TokenDefinitionBuilder<LaiLaiNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .nud((left, parser, lexeme) -> {
                    LaiLaiNode trailingExpression = parser.expression(left);
                    parser.expectSingleLexeme(rparen.getKey());
                    return trailingExpression;
                });

        TokenDefinitionBuilder<LaiLaiNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .discardAfterLexing();

        TokenDefinitionBuilder<LaiLaiNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .nud((left, parser, lexeme) -> parser.expression(left))
                .led((left, parser, lexeme) -> new AdditionNode(left, parser.expression(left)));

        TokenDefinitionBuilder<LaiLaiNode> hat = lb.newToken()
                .matchesString("^")
                .named("hat")
                .led((left, parser, lexeme) -> new HatNode(left, parser.expression(left)));

        TokenDefinitionBuilder<LaiLaiNode> assign = lb.newToken()
                .matchesString("=")
                .named("assign")
                .led((left, parser, lexeme) -> {
                    LaiLaiNode right = parser.expression(left);
                    return new AssignNode(left, right);
                });

        Pattern variablePattern = Pattern.compile("^(float|int|bool) ([a-z]+)$");

        TokenDefinitionBuilder<LaiLaiNode> variableDeclaration = lb.newToken()
                .matchesPattern("(?:float|int|bool) [a-z]+")
                .named("variableDef").nud((left, parser, lexeme) -> {
                    String declaration = lexeme.getText();
                    Matcher matcher = variablePattern.matcher(declaration);
                    boolean matches = matcher.matches();
                    if (!matches) {
                        throw new IllegalStateException("No match for variable declaration'" + declaration + "'");

                    }
                    return new VariableDefNode(left, ExpressionType.forTypeDeclaration(matcher.group(1)), matcher.group(2));
                });

        TokenDefinitionBuilder<LaiLaiNode> variableReference = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variableRef").nud((left, parser, lexeme) -> {
                    String name = lexeme.getText();
                    return new VariableRefNode(name);
                });

        TokenDefinitionBuilder<LaiLaiNode> booleanLiteral = lb.newToken()
                .matchesPattern("true|false")
                .named("bool").nud((left, parser, lexeme) -> new BooleanLiteralNode(Boolean.parseBoolean(lexeme.getText())));

        TokenDefinitionBuilder<LaiLaiNode> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+i")
                .named("int").nud((left, parser, lexeme) -> {
                    String text = lexeme.getText();
                    return new IntegerLiteralNode(left, Integer.parseInt(text.substring(0, text.length() - 1)));
                });

        TokenDefinitionBuilder<LaiLaiNode> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("float").nud((left, parser, lexeme) -> new FloatLiteralNode(left, Float.parseFloat(lexeme.getText())));

        TokenDefinitionBuilder<LaiLaiNode> semicolon = lb.newToken()
                .matchesString(";")
                .named("statement")
                .led((left, parser, lexeme) -> new StatementNode(left, parser.expression(left)));

        final TokenDefinitionBuilder<LaiLaiNode> listEnd = lb.newToken()
                .matchesString("]")
                .named("listEnd");

        final TokenDefinitionBuilder<LaiLaiNode> comma = lb.newToken()
                .matchesString(",")
                .named("comma");

        TokenDefinitionBuilder<LaiLaiNode> listStart = lb.newToken()
                .matchesString("[")
                .named("listStart")
                .nud((left, parser, lexeme) -> {
                    ArrayList<LaiLaiNode> expressions = new ArrayList<>();
                    while (!parser.nextIs(listEnd.getKey())) {
                        expressions.add(parser.expression(left));
                        if (!parser.nextIs(listEnd.getKey())) {
                            parser.expectSingleLexeme(comma.getKey());
                        }
                    }
                    parser.expectSingleLexeme(listEnd.getKey());
                    return new ListNode(left, expressions);
                });

        booleanLiteral.leftBindingPower(first).build();
        lcurly.leftBindingPower(first).build();
        rcurly.leftBindingPower(first).build();
        listEnd.leftBindingPower(first).build();
        lparen.leftBindingPower(first).build();
        rparen.leftBindingPower(first).build();
        listStart.leftBindingPower(first).build();
        comma.leftBindingPower(first).build();
        whitespace.leftBindingPower(first).build();
        integerLiteral.leftBindingPower(first).build();
        floatLiteral.leftBindingPower(first).build();
        variableDeclaration.leftBindingPower(first).build();
        variableReference.leftBindingPower(second).build();
        semicolon.leftBindingPower(third).build();
        assign.leftBindingPower(fourth).build();
        plus.leftBindingPower(fifth).build();
        hat.leftBindingPower(fifth).build();
        Language<LaiLaiNode> l = lb.completeLanguage();
        testOne(l);
        testTwo(l);

        ParseResult<LaiLaiNode> r = l.newLexParser().tryParse("{int a=1i; int b=2i; { int a=3i; a+b}}");
        assertEquals(5, r.getRootNode().evaluate(null));
    }

    private void testTwo(Language<LaiLaiNode> l) {
        ParseResult<LaiLaiNode> r = l.newLexParser().tryParse("{bool b=true;bool c=false;float d=2f;float e=4f;bool f=b^c;float g=d^e;[f,g]}");
        LaiLaiNode root = r.getRootNode();
        assertEquals("(s (x (x (x (x (x (x (= bool(b) true) (= bool(c) false)) (= float(d) 2.0f)) (= float(e) 4.0f)) (= bool(f) (^ b c))) (= float(g) (^ d e))) (l f g )))", root.toString());
        assertEquals(Arrays.asList(Boolean.TRUE, 16f), root.evaluate(null));
    }

    private void testOne(Language<LaiLaiNode> l) {
        String expr = "{int a=5i; a=a+4i; a}";
        ParseResult<LaiLaiNode> result = l.newLexParser().tryParse(expr);
        LaiLaiNode rootNode = result.getRootNode();
        assertEquals("(s (x (x (= int(a) 5i) (= a (+ a 4i))) a))", rootNode.toString());
        assertEquals(9, rootNode.evaluate(null));
    }
}
