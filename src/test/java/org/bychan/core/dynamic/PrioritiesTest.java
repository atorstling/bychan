package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void prioritiesAreObeyed() {
        Language<Integer> l = new LanguageBuilder<Integer>().named("parantheses")
                .newToken().named("mult").leftBindingPower(1).matchesString("*").infixParseAs((previous, match, parser, lbp) -> previous * parser.subExpression()).end()
                .newToken().named("plus").leftBindingPower(2).matchesString("+").infixParseAs((previous, match, parser, lbp) -> previous + parser.subExpression()).end()
                .newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").prefixParseAs((previous, match, parser, lbp) -> Integer.parseInt(match.getText())).end()
                .completeLanguage();
        assertEquals((Integer) 9, l.getLexParser().tryParse("1+2*3").getRootNode());
    }
}
