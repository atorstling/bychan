package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void prioritiesAreObeyed() {
        Language<Integer> l = new LanguageBuilder<Integer>().named("parantheses")
                .newToken().named("mult").leftBindingPower(1).matchesString("*").led((previous, parser, lexeme) -> previous * parser.subExpression(previous)).end()
                .newToken().named("plus").leftBindingPower(2).matchesString("+").led((previous, parser, lexeme) -> previous + parser.subExpression(previous)).end()
                .newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText())).end()
                .completeLanguage();
        assertEquals((Integer) 9, l.newLexParser().tryParse("1+2*3").getRootNode());
    }
}
