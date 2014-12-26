package org.bychan.fluid;

import org.bychan.core.Bychan;
import org.bychan.core.TokenType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void doit() {
        Language<Integer> l = new Bychan().<Integer>newFluidLanguage().named("parantheses")
                .newLevelToken().named("mult").matchesString("*").infixParseAs((match, previous, parser) -> previous * parser.subExpression())
                .newLevelToken().named("plus").matchesString("+").infixParseAs((match, previous, parser) -> previous + parser.subExpression())
                .newLevelToken().named("num").matchesPattern("[0-9]+").standaloneParseAs((previous, match) -> Integer.parseInt(match.getText()))
                .completeLanguage();
        //ssertEquals(0, ((GenericTokenType<Integer>) l.getTokenType("plus")).getLevel());
        //assertEquals(1, ((GenericTokenType<Integer>) l.getTokenType("mult")).getLevel());
        //assertEquals(2, ((GenericTokenType<Integer>) l.getTokenType("num")).getLevel());
        assertEquals((Integer) 9, l.getLexParser().tryParse("1+2*3").getRootNode());
    }
}
