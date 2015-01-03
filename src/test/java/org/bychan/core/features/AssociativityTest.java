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
        Language<Integer> l = new LanguageBuilder<Integer>().named("pow")
                .startToken().named("number").matchesPattern("[0-9]+").nud((previous, match, parser, currentPowerFloor) -> Integer.parseInt(match.getText())).end()
                .newToken().named("pow").matchesString("^").led((previous, match, parser, currentPowerFloor) -> (int) Math.pow(previous, parser.subExpression(previous, currentPowerFloor - 1))).end().completeLanguage();
        assertEquals((Integer) 256, l.getLexParser().parse("2^2^3"));
    }
}