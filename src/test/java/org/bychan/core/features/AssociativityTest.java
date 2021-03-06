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
        LanguageBuilder<Integer> lb = new LanguageBuilder<>("pow");
        lb.newToken().named("number").matchesPattern("[0-9]+").nud((left, parser, lexeme) -> Integer.parseInt(lexeme.text())).build();
        lb.newToken().named("pow").matchesString("^").led((left, parser, lexeme) -> (int) Math.pow(left, parser.expr(left, lexeme.lbp() - 1))).build();
        Language<Integer> l = lb.build();
        assertEquals((Integer) 256, l.newLexParser().tryParse("2^2^3", p -> p.expr(null, 0)).root());
    }
}
