package com.torstling.tdop.core;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class EndTokenTypeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void toTokenNotSupported() {
        EndTokenType.get().toToken(mock(LexingMatch.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPatternNotSupported() {
        EndTokenType.get().getPattern();
    }

    @Test
    public void isIncluded() {
        assertTrue(EndTokenType.get().include());
    }

}