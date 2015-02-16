package org.bychan.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BoundedFifoTest {

    @Test
    public void putGet() throws Exception {
        BoundedFifo<String> b = new BoundedFifo<>(2);
        b.put("a");
        b.put("b");
        assertEquals(b.getFromFront(0), "b");
        assertEquals(b.getFromFront(1), "a");
    }

    @Test
    public void overflow() throws Exception {
        BoundedFifo<String> b = new BoundedFifo<>(1);
        b.put("a");
        b.put("b");
        assertEquals(b.getFromFront(0), "b");
        try {
            b.getFromFront(1);
            fail("expected exception");
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    @Test
    public void overflowWithTwo() throws Exception {
        BoundedFifo<String> b = new BoundedFifo<>(2);
        b.put("a");
        b.put("b");
        assertEquals(b.getFromFront(0), "b");
        assertEquals(b.getFromFront(1), "a");
        b.put("c");
        assertEquals(b.getFromFront(0), "c");
        assertEquals(b.getFromFront(1), "b");
    }
}