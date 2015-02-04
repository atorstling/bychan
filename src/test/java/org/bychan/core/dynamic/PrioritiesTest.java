package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void prioritiesAreObeyed() {
        LanguageBuilder<Integer> lb = new LanguageBuilder<>("parantheses");
        lb.newToken().named("mult").leftBindingPower(1).matchesString("*").led((left, parser, lexeme) -> left * parser.expression(left)).build();
        lb.newToken().named("plus").leftBindingPower(2).matchesString("+").led((left, parser, lexeme) -> left + parser.expression(left)).build();
        lb.newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").nud((left, parser, lexeme) -> Integer.parseInt(lexeme.getText())).build();
        Language<Integer> l = lb.completeLanguage();
        assertEquals((Integer) 9, l.newLexParser().tryParse("1+2*3").getRootNode());
    }
}
