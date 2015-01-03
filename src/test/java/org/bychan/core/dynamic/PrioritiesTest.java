package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void doit() {
        Language<Integer> l = new LanguageBuilder<Integer>().named("parantheses")
                .newToken().named("mult").leftBindingPower(1).matchesString("*").infixParseAs((previous, match, parser, lbp) -> previous * parser.subExpression())
                .newToken().named("plus").leftBindingPower(2).matchesString("+").infixParseAs((previous, match, parser, lbp) -> previous + parser.subExpression())
                .newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").prefixParseAs((previous, match, parser, lbp) -> Integer.parseInt(match.getText()))
                .completeLanguage();
        //ssertEquals(0, ((DynamicTokenType<Integer>) l.getTokenType("plus")).getLeftBindingPower());
        //assertEquals(1, ((DynamicTokenType<Integer>) l.getTokenType("mult")).getLeftBindingPower());
        //assertEquals(2, ((DynamicTokenType<Integer>) l.getTokenType("num")).getLeftBindingPower());
        assertEquals((Integer) 9, l.getLexParser().tryParse("1+2*3").getRootNode());
    }
}
