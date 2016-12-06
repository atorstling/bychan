package org.bychan.core.examples;

import org.bychan.core.basic.*;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alext on 2015-01-05.
 */
public class ReadmeExamples {

    @Test
    public void simpleCalc() {
        LanguageBuilder<Long> lb = new LanguageBuilder<>("simpleCalc");
        lb.newToken().named("digit")
                .matchesPattern("[0-9]+")
                .nud((left, parser, lexeme) -> Long.parseLong(lexeme.text()))
                .build();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((left, parser, lexeme) -> left + parser.expr(left, lexeme.lbp()))
                .build();
        lb.powerUp();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((left, parser, lexeme) -> left * parser.expr(left, lexeme.lbp()))
                .build();
        Language<Long> language = lb.build();
        LexParser<Long> lexParser = language.newLexParser();
        assertEquals((Long) 7L, lexParser.tryParse("1+2*3", p -> p.expr(null, 0)).root());
    }

    @Test
    public void toRpn() {
        LanguageBuilder<String> lb = new LanguageBuilder<>("calc");
        lb.newToken().named("whitespace")
                .matchesWhitespace()
                .discardAfterLexing()
                .build();
        lb.newToken().named("digit").matchesPattern("[0-9]+")
                .nud((left, parser, lexeme) -> lexeme.text())
                .build();
        lb.newToken()
                .named("rparen")
                .matchesString(")")
                .build();
        lb.newToken().named("lparen").matchesString("(").nud((left, parser, lexeme) -> {
            String next = parser.expr(left, lexeme.lbp());
            parser.swallow("rparen");
            return next;
        }).build();
        lb.powerUp();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((left, parser, lexeme) -> "(+ " + left + " " + parser.expr(left, lexeme.lbp()) + ")")
                .build();
        lb.powerUp();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((left, parser, lexeme) -> "(* " + left + " " + parser.expr(left, lexeme.lbp()) + ")")
                .build();
        Language<String> language = lb.build();
        LexParser<String> lexParser = language.newLexParser();
        assertEquals("(+ (* (+ 1 2) 3) 5)", lexParser.tryParse("( 1 + 2 ) * 3 + 5", p -> p.expr(null, 0)).root());
    }

    @Test
    public void boolLogic() {
        LanguageBuilder<BoolNode> lb = new LanguageBuilder<>("boolLogic");
        lb.newToken().named("literal")
                .matchesPattern("true|false")
                .nud((left, parser, lexeme) -> new LiteralNode(Boolean.parseBoolean(lexeme.text())))
                .build();
        lb.newToken().named("and")
                .matchesString("&&")
                .led((left, parser, lexeme) -> new AndNode(left, parser.expr(left, lexeme.lbp())))
                .build();
        Language<BoolNode> l = lb.build();
        LexParser<BoolNode> lexParser = l.newLexParser();
        BoolNode one = lexParser.tryParse("false&&false&&false", p -> p.expr(null, 0)).root();
        assertFalse(one.evaluate());
        BoolNode two = lexParser.tryParse("true&&false&&true", p -> p.expr(null, 0)).root();
        assertFalse(two.evaluate());
        BoolNode three = lexParser.tryParse("true&&true&&true", p -> p.expr(null, 0)).root();
        assertTrue(three.evaluate());
    }

    interface BoolNode extends Evaluatable<Boolean> {
        Boolean evaluate();
    }

    class LiteralNode implements BoolNode {
        final boolean value;

        public LiteralNode(boolean value) {
            this.value = value;
        }

        @Override
        public Boolean evaluate() {
            return value;
        }
    }

    class AndNode implements BoolNode {
        final BoolNode left;
        final BoolNode right;

        public AndNode(BoolNode left, BoolNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public Boolean evaluate() {
            return left.evaluate() && right.evaluate();
        }
    }

    interface VNode {

    }

    class VariableList implements VNode {

        private final List<Variable> variables;

        public VariableList(List<Variable> variables) {
            this.variables = variables;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VariableList that = (VariableList) o;

            return variables.equals(that.variables);

        }

        @Override
        public int hashCode() {
            return variables.hashCode();
        }

        @Override
        public String toString() {
            return "VariableList{" +
                    "variables=" + variables +
                    '}';
        }
    }

    class Variable implements VNode {
        @NotNull
        private final String type;
        @NotNull
        private final String name;
        @NotNull
        private final String value;

        public Variable(@NotNull String type, @NotNull String name, @NotNull String value) {

            this.type = type;
            this.name = name;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Variable variable = (Variable) o;

            return type.equals(variable.type) && name.equals(variable.name) && value.equals(variable.value);
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + (name.hashCode());
            result = 31 * result + (value.hashCode());
            return result;
        }
    }

    @Test
    public void statements() throws Exception {
        final LanguageBuilder<VNode> b = new LanguageBuilder<>("variables");
        b.newToken().named("whitespace").matchesWhitespace().discardAfterLexing();
        b.newToken().named("semicolon").matchesString(";").build();
        b.newToken()
                .named("vardecl")
                .matchesPattern("(int|float) (\\w+)=([0-9]+)")
                .nud((left, parser, lexeme) -> new Variable(lexeme.group(1), lexeme.group(2), lexeme.group(3)))
                .build();
        final Language<VNode> lang = b.build();
        final LexParser<VNode> lp = lang.newLexParser();

        final ParseResult<VNode> pr = lp.tryParse("int a=4;float b=72;", this::declList);
        assertEquals(new VariableList(Arrays.asList(new Variable("int", "a", "4"), new Variable("float", "b", "72"))), pr.root());
    }

    @NotNull
    private VariableList declList(Parser<VNode> parser) {
        final ArrayList<Variable> variables = new ArrayList<>();
        while (!parser.peek().isA("end")) {
            final Lexeme<VNode> lexeme = parser.swallow("vardecl");
            final Variable variable = (Variable) parser.nud(lexeme, null);
            variables.add(variable);
            parser.swallow("semicolon");
        }
        return new VariableList(variables);
    }
}
