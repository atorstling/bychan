package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void prioritiesAreObeyed() {
        LanguageBuilder<Integer> lb = new LanguageBuilder<Integer>().named("parantheses");
        lb.newToken().named("mult").leftBindingPower(1).matchesString("*").led((previous, parser, lexeme) -> previous * parser.subExpression(previous)).build();
        lb.newToken().named("plus").leftBindingPower(2).matchesString("+").led((previous, parser, lexeme) -> previous + parser.subExpression(previous)).end();
        lb.newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText())).end();
        Language<Integer> l = lb.completeLanguage();
        assertEquals((Integer) 9, l.newLexParser().tryParse("1+2*3").getRootNode());
    }
}
