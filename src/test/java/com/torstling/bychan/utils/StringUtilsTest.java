package com.torstling.bychan.utils;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void repeat() {
        assertEquals("a a a ", StringUtils.repeat("a ", 3));
    }

}