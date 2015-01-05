package org.bychan.core.features;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alext on 2015-01-03.
 */
public class AssociativityTest {

    @Test
    public void right() {
        LanguageBuilder<Integer> lb = new LanguageBuilder<Integer>().named("pow");
        lb.startToken().named("number").matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText())).build();
        lb.newToken().named("pow").matchesString("^").led((previous, parser, lexeme) -> (int) Math.pow(previous, parser.subExpression(previous, lexeme.leftBindingPower() - 1))).end();
        Language<Integer> l = lb.completeLanguage();
        assertEquals((Integer) 256, l.newLexParser().parse("2^2^3"));
    }
}
