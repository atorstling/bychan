package org.bychan.core.examples;

import org.bychan.core.basic.LexParser;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinition;

import static org.junit.Assert.assertEquals;

/**
 * Created by alext on 2015-01-05.
 */
public class CalculatorExample {

    public static void main(String[] args) {
        LanguageBuilder<Long> lb = new LanguageBuilder<>("calc");
        lb.newToken().matchesWhitespace().discardAfterLexing();
        lb.newToken().matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Long.parseLong(lexeme.getText())).build();
        lb.newToken().matchesString("+").led((previous, parser, lexeme) -> previous + parser.expression(previous)).build();
        lb.newToken().matchesString("-").led((previous, parser, lexeme) -> previous - parser.expression(previous)).build();
        lb.newToken().matchesString("*").led((previous, parser, lexeme) -> previous * parser.expression(previous)).build();
        TokenDefinition<Long> rparen = lb.newToken().matchesString(")").build();
        lb.newToken().matchesString("(").led((previous, parser, lexeme) -> {
            Long trailing = parser.expression(previous);
            parser.expectSingleLexeme(rparen.getKey());
            return trailing;
        }).build();
        LexParser<Long> lexParser = lb.completeLanguage().newLexParser();
        assertEquals((Long) 26l, lexParser.parse("1+((2+3)*5)"));
    }
}
