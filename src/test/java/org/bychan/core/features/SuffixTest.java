package org.bychan.core.features;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alext on 2015-01-03.
 */
public class SuffixTest {

    @Test
    public void supportsInfixOperators() {
        LanguageBuilder<Object> lb = new LanguageBuilder<>("test");
        lb.newToken().named("variable").matchesPattern("[a-z]{1}").nud((left, parser, lexeme) -> lexeme.getText().charAt(0)).build();
        lb.newToken().named("inc").matchesString("++").led((left, parser, lexeme) -> (char) (((char) left) + 1)).build();
        Language<Object> l = lb.completeLanguage();
        assertEquals('d', l.newLexParser().tryParse("c++").getRootNode());
    }
}
