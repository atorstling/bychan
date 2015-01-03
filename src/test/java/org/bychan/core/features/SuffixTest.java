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
        LanguageBuilder<Object> lb = new LanguageBuilder<>().named("test");
        lb.addToken(lb.newToken().named("variable").matchesPattern("[a-z]{1}").nud((previous, match, parser, currentBindingPower) -> match.getText().charAt(0)).build());
        lb.addToken(lb.newToken().named("inc").matchesString("++").led((previous, match, parser, currentBindingPower) -> (char) (((char) previous) + 1)).build());
        Language<Object> l = lb.completeLanguage();
        assertEquals('d', l.getLexParser().parse("c++"));
    }
}
