package org.bychan.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BoundedFifoTest {

    @Test
    public void putGet() {
        BoundedFifo<String> b = new BoundedFifo<>(2);
        b.putLast("a");
        b.putLast("b");
        assertEquals("b", b.findFromFront(0));
        assertEquals("a", b.findFromFront(1));
        assertEquals(null, b.findFromFront(2));
    }

    @Test
    public void overflow() {
        BoundedFifo<String> b = new BoundedFifo<>(1);
        b.putLast("a");
        b.putLast("b");
        assertEquals("b", b.findFromFront(0));
        assertEquals(null, b.findFromFront(1));
    }

    @Test
    public void overflowWithTwo() {
        BoundedFifo<String> b = new BoundedFifo<>(2);
        b.putLast("a");
        b.putLast("b");
        assertEquals("b", b.findFromFront(0));
        assertEquals("a", b.findFromFront(1));
        b.putLast("c");
        assertEquals("c", b.findFromFront(0));
        assertEquals("b", b.findFromFront(1));
    }

    @Test
    public void getFromFront() {
        BoundedFifo<String> b = new BoundedFifo<>(1);
        b.putLast("a");
        assertEquals("a", b.getFromFront(0));
        try {
            b.getFromFront(1);
            fail("expected exception");
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}