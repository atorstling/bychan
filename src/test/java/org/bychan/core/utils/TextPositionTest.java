package org.bychan.core.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextPositionTest {

    @Test
    public void getters() {
        TextPosition tp = new TextPosition(1, 2, 3);
        assertEquals(1, tp.getIndex());
        assertEquals(2, tp.getRow());
        assertEquals(3, tp.getCol());
    }

}