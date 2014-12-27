package org.bychan.generic;

import org.bychan.core.Bychan;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void doit() {
        Language<Integer> l = new Bychan().<Integer>newFluidLanguage().named("parantheses")
                .newToken().named("mult").leftBindingPower(1).matchesString("*").infixParseAs((match, previous, parser) -> previous * parser.subExpression())
                .newToken().named("plus").leftBindingPower(2).matchesString("+").infixParseAs((match, previous, parser) -> previous + parser.subExpression())
                .newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").standaloneParseAs((previous, match) -> Integer.parseInt(match.getText()))
                .completeLanguage();
        //ssertEquals(0, ((GenericTokenType<Integer>) l.getTokenType("plus")).getLeftBindingPower());
        //assertEquals(1, ((GenericTokenType<Integer>) l.getTokenType("mult")).getLeftBindingPower());
        //assertEquals(2, ((GenericTokenType<Integer>) l.getTokenType("num")).getLeftBindingPower());
        assertEquals((Integer) 9, l.getLexParser().tryParse("1+2*3").getRootNode());
    }
}
