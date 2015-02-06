package org.bychan.core.features;

import org.bychan.core.TokenMatchResult;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alext on 2015-02-06.
 */
public class CustomMatcherTest {

    @Test
    public void carryValueOverFromLexing() {
        LanguageBuilder<Object> lb = new LanguageBuilder<>();
        lb.newToken().named("test")
                .matches((input, searchStart) -> {
                    int end = searchStart + 1;
                    String lexerValue = input.substring(searchStart, end);
                    return TokenMatchResult.create(lexerValue, end);
                })
                .nud((left, parser, lexeme) -> {
                    String lexerValue = (String) lexeme.getLexerValue();
                    assert lexerValue != null;
                    return lexerValue;
                }).build();
        Language<Object> l = lb.completeLanguage();
        Assert.assertEquals("a", l.newLexParser().parse("a"));
    }
}
