package com.torstling.tdop.fluid.minilang;


import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder;
import com.torstling.tdop.fluid.TokenDefinition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class MiniLangTest {
    @Test
    public void test() {
        LanguageBuilder<LaiLaiNode> lb = new LanguageBuilder<>();

        final TokenDefinition<LaiLaiNode> rcurly = lb.newToken()
                .matchesString("}")
                .named("rcurly")
                .build();

        final TokenDefinition<LaiLaiNode> lcurly = lb.newToken()
                .matchesString("{")
                .named("lcurly")
                .prefixParseAs((previous, match, parser) -> {
                    Scope scope = (previous == null) ? new RootScope() : new NestedScope(previous.getScope());
                    ScopeNode scopeNode = new ScopeNode(scope);
                    LaiLaiNode expression = parser.expression(scopeNode);
                    scopeNode.setChild(expression);
                    parser.expectSingleToken(rcurly);
                    return scopeNode;
                })
                .build();

        final TokenDefinition<LaiLaiNode> rparen = lb.newToken()
                .matchesString(")")
                .named("rparen")
                .build();

        TokenDefinition<LaiLaiNode> lparen = lb.newToken()
                .matchesString("(")
                .named("lparen")
                .prefixParseAs((previous, match, parser) -> {
                    LaiLaiNode trailingExpression = parser.expression();
                    parser.expectSingleToken(rparen);
                    return trailingExpression;
                }).build();

        TokenDefinition<LaiLaiNode> whitespace = lb.newToken()
                .matchesPattern("\\s+")
                .named("whitespace")
                .ignoredWhenParsing()
                .build();

        TokenDefinition<LaiLaiNode> plus = lb.newToken()
                .matchesString("+")
                .named("plus")
                .prefixParseAs((previous, match, parser) -> parser.expression())
                .infixParseAs((match, previous, parser) -> new AdditionNode(previous, parser.expression()))
                .build();

        TokenDefinition<LaiLaiNode> hat = lb.newToken()
                .matchesString("^")
                .named("hat")
                .infixParseAs((match, previous, parser) -> new HatNode(previous, parser.expression()))
                .build();

        TokenDefinition<LaiLaiNode> assign = lb.newToken()
                .matchesString("=")
                .named("assign")
                .infixParseAs((match, previous, parser) -> {
                    LaiLaiNode right = parser.expression();
                    return new AssignNode(previous, right);
                })
                .build();

        TokenDefinition<LaiLaiNode> variableDeclaration = lb.newToken()
                .matchesPattern("(?:float|int|bool) [a-z]+")
                .named("variableDef")
                .standaloneParseAs((previous, match) -> {
                    String declaration = match.getText();
                    Pattern variablePattern = Pattern.compile("^(float|int|bool) ([a-z]+)$");
                    Matcher matcher = variablePattern.matcher(declaration);
                    boolean matches = matcher.matches();
                    if (!matches) {
                        throw new IllegalStateException("No match for variable declaration'" + declaration + "'");

                    }
                    return new VariableDefNode(previous, ExpressionType.forTypeDeclaration(matcher.group(1)), matcher.group(2));
                }).build();

        TokenDefinition<LaiLaiNode> variableReference = lb.newToken()
                .matchesPattern("[a-z]+")
                .named("variableRef")

                .standaloneParseAs((symbolTable, match) -> {
                    String name = match.getText();
                    return new VariableRefNode(name);
                }).build();

        TokenDefinition<LaiLaiNode> booleanLiteral = lb.newToken()
                .matchesPattern("true|false")
                .named("bool")
                .standaloneParseAs((previous, match) -> new BooleanLiteralNode(Boolean.parseBoolean(match.getText()))).build();

        TokenDefinition<LaiLaiNode> integerLiteral = lb.newToken()
                .matchesPattern("[0-9]+i")
                .named("int")
                .standaloneParseAs((previous, match) -> {
                    String text = match.getText();
                    return new IntegerLiteralNode(previous, Integer.parseInt(text.substring(0, text.length() - 1)));
                }).build();

        TokenDefinition<LaiLaiNode> floatLiteral = lb.newToken()
                .matchesPattern("[0-9]+f")
                .named("float")
                .standaloneParseAs((previous, match) -> new FloatLiteralNode(previous, Float.parseFloat(match.getText()))).build();

        TokenDefinition<LaiLaiNode> semicolon = lb.newToken()
                .matchesString(";")
                .named("statement")
                .infixParseAs((match, previous, parser) -> new StatementNode(previous, parser.expression())).build();

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
                .prefixParseAs((previous, match, parser) -> {
                    ArrayList<LaiLaiNode> expressions = new ArrayList<>();
                    while (!parser.nextIs(listEnd)) {
                        expressions.add(parser.expression());
                        if (!parser.nextIs(listEnd)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(listEnd);
                    return new ListNode(previous, expressions);
                }).build();

        Language<LaiLaiNode> l = lb
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

        ParseResult<LaiLaiNode> r = l.getParser().tryParse("{int a=1i; int b=2i; { int a=3i; a+b}}");
        assertEquals(5, r.getRootNode().evaluate(null));
    }

    private void testTwo(Language<LaiLaiNode> l) {
        ParseResult<LaiLaiNode> r = l.getParser().tryParse("{bool b=true;bool c=false;float d=2f;float e=4f;bool f=b^c;float g=d^e;[f,g]}");
        LaiLaiNode root = r.getRootNode();
        assertEquals("(s (x (x (x (x (x (x (= bool(b) true) (= bool(c) false)) (= float(d) 2.0f)) (= float(e) 4.0f)) (= bool(f) (^ b c))) (= float(g) (^ d e))) (l f g )))", root.toString());
        assertEquals(Arrays.<Object>asList(Boolean.TRUE, 16f), root.evaluate(null));
    }

    private void testOne(Language<LaiLaiNode> l) {
        String expr = "{int a=5i; a=a+4i; a}";
        ParseResult<LaiLaiNode> result = l.getParser().tryParse(expr);
        LaiLaiNode rootNode = result.getRootNode();
        assertEquals("(s (x (x (= int(a) 5i) (= a (+ a 4i))) a))", rootNode.toString());
        assertEquals(9, rootNode.evaluate(null));
    }
}
