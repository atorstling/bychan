package org.bychan.core.dynamic;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AnyPermutationMatcherTest {

    @Test
    public void singleMatch() {
        AnyPermutationMatcher m = new AnyPermutationMatcher(Arrays.asList("a", "b", "c"), Collections.singleton(":"));
        TokenMatchResult matchResult = m.tryMatch("qqqa", 3);
        assertEquals(TokenMatchResult.create(4), matchResult);
    }

    @Test
    public void doubleMatch() {
        AnyPermutationMatcher m = new AnyPermutationMatcher(Arrays.asList("a", "b", "c"), Collections.singleton(":"));
        TokenMatchResult matchResult = m.tryMatch("qqqab", 3);
        assertEquals(TokenMatchResult.create(5), matchResult);
    }

    @Test
    public void doubleMatchWithSeparator() {
        AnyPermutationMatcher m = new AnyPermutationMatcher(Arrays.asList("a", "b", "c"), Collections.singleton(":"));
        TokenMatchResult matchResult = m.tryMatch("qqqa:b:", 3);
        assertEquals(TokenMatchResult.create(7), matchResult);
    }

    @Test
    public void duplicateMatch() {
        AnyPermutationMatcher m = new AnyPermutationMatcher(Arrays.asList("a", "b", "c"), Collections.singleton(":"));
        try {
            TokenMatchResult result = m.tryMatch("qqqa:a:a", 3);
            fail("expected exception, got " + result);
        } catch (IllegalStateException e) {
            assertEquals("Duplicates: [a]", e.getMessage());
        }
    }

}