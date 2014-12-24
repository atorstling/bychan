package org.bychan.fluid;

import org.bychan.core.Bychan;
import org.bychan.core.LexingMatch;
import org.bychan.fluid.minilang.IntegerLiteralNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    //@Test
    public void doit() {
        Language<Integer> l = new Bychan().<Integer>newFluidLanguage().named("parantheses")
                .newLevelToken().named("plus").matchesString("+").infixParseAs((match, previous, parser) -> previous + parser.subExpression())
                .newLevelToken().named("mult").matchesString("*").infixParseAs((match, previous, parser) -> previous * parser.subExpression())
                .newLevelToken().named("num").matchesPattern("[0-9]+").standaloneParseAs((previous, match) -> Integer.parseInt(match.getText()))
                .completeLanguage();
        assertEquals((Integer) 7, l.getParser().tryParse("1+2*3").getRootNode());
    }
}
