package org.bychan.core.examples;

import org.bychan.core.basic.LexParser;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinition;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alext on 2015-01-05.
 */
public class ReadmeExamples {

    @Test
    public void simpleCalc() {
        LanguageBuilder<Long> lb = new LanguageBuilder<>("simpleCalc");
        lb.newToken().named("digit").matchesPattern("[0-9]+")
                .nud((previous, parser, lexeme) -> Long.parseLong(lexeme.getText()))
                .build();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((previous, parser, lexeme) -> previous + parser.expression(previous))
                .build();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((previous, parser, lexeme) -> previous * parser.expression(previous))
                .build();
        Language<Long> language = lb.completeLanguage();
        LexParser<Long> lexParser = language.newLexParser();
        assertEquals((Long) 7l, lexParser.parse("1+2*3"));
    }

    @Test
    public void toRpn() {
        LanguageBuilder<String> lb = new LanguageBuilder<>("calc");
        lb.newToken().named("whitespace")
                .matchesWhitespace()
                .discardAfterLexing()
                .build();
        lb.newToken().named("digit").matchesPattern("[0-9]+")
                .nud((previous, parser, lexeme) -> lexeme.getText())
                .build();
        TokenDefinition<String> rparen = lb.newToken()
                .named("rparen")
                .matchesString(")")
                .build();
        lb.newToken().named("lparen").matchesString("(").nud((previous, parser, lexeme) -> {
            String next = parser.expression(previous);
            parser.expectSingleLexeme(rparen.getKey());
            return next;
        }).build();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((previous, parser, lexeme) -> "(+ " + previous + " " + parser.expression(previous) + ")")
                .build();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((previous, parser, lexeme) -> "(* " + previous + " " + parser.expression(previous) + ")")
                .build();
        Language<String> language = lb.completeLanguage();
        LexParser<String> lexParser = language.newLexParser();
        assertEquals("(+ (* (+ 1 2) 3) 5)", lexParser.parse("( 1 + 2 ) * 3 + 5"));
    }

    interface BoolNode {
        boolean evaluate();
    }

    class LiteralNode implements BoolNode {
        boolean value;

        public LiteralNode(boolean value) {
            this.value = value;
        }

        @Override
        public boolean evaluate() {
            return value;
        }
    }

    class AndNode implements BoolNode {
        public BoolNode left;
        public BoolNode right;

        public AndNode(BoolNode left, BoolNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean evaluate() {
            return left.evaluate() && right.evaluate();
        }
    }
    @Test
    public void boolLogic() {
        LanguageBuilder<BoolNode> lb = new LanguageBuilder<>("boolLogic");
        lb.newToken().named("literal")
                .matchesPattern("true|false")
                .nud((previous, parser, lexeme) -> new LiteralNode(Boolean.parseBoolean(lexeme.getText())))
                .build();
        lb.newToken().named("and")
                .matchesString("&&")
                .led((previous, parser, lexeme) -> new AndNode(previous, parser.expression(previous)))
                .build();
        Language<BoolNode> l = lb.completeLanguage();
        LexParser<BoolNode> lexParser = l.newLexParser();
        BoolNode one = lexParser.parse("false&&false&&false");
        assertFalse(one.evaluate());
        BoolNode two = lexParser.parse("true&&false&&true");
        assertFalse(two.evaluate());
        BoolNode three = lexParser.parse("true&&true&&true");
        assertTrue(three.evaluate());
    }


}
