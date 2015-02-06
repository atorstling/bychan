package org.bychan.core.features;

import org.bychan.core.TokenMatcher;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
                .matches(new CustomMatcher())
                .nud((left, parser, lexeme) -> {
                    String savedResult = ((CustomMatcher) lexeme.getMatcher()).getSavedResult();
                    assert savedResult != null;
                    return savedResult;
                }).build();
        Language<Object> l = lb.completeLanguage();
        Assert.assertEquals("a", l.newLexParser().parse("a"));
    }

    private static class CustomMatcher implements TokenMatcher {
        @Nullable
        private String savedResult;

        @Override
        public int tryMatch(@NotNull String input, int searchStart) {
            int end = searchStart + 1;
            savedResult = input.substring(searchStart, end);
            return end;
        }

        @Nullable
        public String getSavedResult() {
            return savedResult;
        }
    }
}
