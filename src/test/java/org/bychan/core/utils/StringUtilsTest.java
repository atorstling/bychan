package org.bychan.core.utils;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void repeat() {
        assertEquals("a a a ", StringUtils.repeat("a ", 3));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void getTextPositionEmptyString() {
        StringUtils.getTextPosition("", 0);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void getTextPositionOutOfBounds() {
        StringUtils.getTextPosition(" ", 1);
    }

    @Test
    public void getTextPositionAtFirst() {
        assertEquals(new TextPosition(1, 1), StringUtils.getTextPosition(" ", 0));
    }

    @Test
    public void getTextPositionAtNewline() {
        assertEquals(new TextPosition(1, 1), StringUtils.getTextPosition("\n", 0));
    }

    @Test
    public void getTextPositionAfterNewline() {
        assertEquals(new TextPosition(2,1), StringUtils.getTextPosition("\n ", 1));
    }

    @Test
    public void getTextPositionBeforeCRLF() {
        assertEquals(new TextPosition(1,1), StringUtils.getTextPosition(" \r\n ", 0));
    }

    @Test
    public void getTextPositionStartCRLF() {
        assertEquals(new TextPosition(1,2), StringUtils.getTextPosition(" \r\n ", 1));
    }

    @Test
    public void getTextPositionMiddleOfCRLF() {
        assertEquals(new TextPosition(1,3), StringUtils.getTextPosition(" \r\n ", 2));
    }

    @Test
    public void getTextPositionAfterCRLF() {
        assertEquals(new TextPosition(2,1), StringUtils.getTextPosition(" \r\n ", 3));
    }

    @Test
    public void getTextPositionSeveralLines() {
        String test = "a\r\nb\ncde";
        assertEquals(new TextPosition(3,2), StringUtils.getTextPosition(test, test.indexOf("d")));
    }
}