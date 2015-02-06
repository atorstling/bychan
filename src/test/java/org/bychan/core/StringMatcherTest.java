package org.bychan.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringMatcherTest {

    @Test
    public void matches() {
        StringMatcher sm = new StringMatcher("wo");
        assertEquals(null, sm.tryMatch("hello world", 0));
        assertEquals(TokenMatchResult.create(8), sm.tryMatch("hello world", 6));
    }

}