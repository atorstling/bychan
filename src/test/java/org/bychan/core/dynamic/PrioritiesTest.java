package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrioritiesTest {
    @Test
    public void prioritiesAreObeyed() {
        LanguageBuilder<Integer> lb = new LanguageBuilder<>("parantheses");
        lb.newToken().named("mult").leftBindingPower(1).matchesString("*").led((previous, parser, lexeme) -> previous * parser.subExpression(previous)).build();
        TokenDefinitionBuilder<Integer> integerTokenDefinitionBuilder1 = lb.newToken().named("plus").leftBindingPower(2).matchesString("+").led((previous, parser, lexeme) -> previous + parser.subExpression(previous));
        integerTokenDefinitionBuilder1.build();
        TokenDefinitionBuilder<Integer> integerTokenDefinitionBuilder = lb.newToken().named("num").leftBindingPower(3).matchesPattern("[0-9]+").nud((previous, parser, lexeme) -> Integer.parseInt(lexeme.getText()));
        integerTokenDefinitionBuilder.build();
        Language<Integer> l = lb.completeLanguage();
        assertEquals((Integer) 9, l.newLexParser().tryParse("1+2*3").getRootNode());
    }
}
