package org.bychan.core.examples;

import org.bychan.core.basic.LexParser;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alext on 2015-01-05.
 */
public class CalculatorExample {

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
    public void simpleCalcRpn() {
        LanguageBuilder<String> lb = new LanguageBuilder<>("simpleCalc");
        lb.newToken().named("digit").matchesPattern("[0-9]+")
                .nud((previous, parser, lexeme) -> lexeme.getText())
                .build();
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
        assertEquals("(+ 1 (* 2 3))", lexParser.parse("1+2*3"));
    }

    @Test
    public void calc() {
        LanguageBuilder<Long> lb = new LanguageBuilder<>("calc");
        lb.newToken().named("whitespace")
                .matchesWhitespace()
                .discardAfterLexing();
        lb.newToken().named("digit")
                .matchesPattern("[0-9]+")
                .nud((previous, parser, lexeme) -> Long.parseLong(lexeme.getText()))
                .build();
        TokenDefinition<Long> rparen = lb.newToken()
                .named("rparen")
                .matchesString(")")
                .build();
        lb.newToken().named("lparen").matchesString("(").nud((previous, parser, lexeme) -> {
            Long next = parser.expression(previous);
            parser.expectSingleLexeme(rparen.getKey());
            return next;
        }).build();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((previous, parser, lexeme) -> previous + parser.expression(previous))
                .build();
        lb.newToken().named("minus")
                .matchesString("-")
                .led((previous, parser, lexeme) -> previous - parser.expression(previous))
                .build();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((previous, parser, lexeme) -> previous * parser.expression(previous))
                .build();
        Language<Long> language = lb.completeLanguage();
        LexParser<Long> lexParser = language.newLexParser();
        assertEquals((Long) 3l, lexParser.parse("1+2*3-4"));
        assertEquals((Long) 7l, lexParser.parse("(1+2)*3-2"));
    }
}
