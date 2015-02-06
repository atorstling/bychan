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
                    String lexerResult = input.substring(searchStart, end);
                    return TokenMatchResult.create(lexerResult, end);
                })
                .nud((left, parser, lexeme) -> {
                    String lexerResult = (String) lexeme.getLexerResult();
                    assert lexerResult != null;
                    return lexerResult;
                }).build();
        Language<Object> l = lb.completeLanguage();
        Assert.assertEquals("a", l.newLexParser().parse("a"));
    }
}
