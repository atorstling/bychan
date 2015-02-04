package org.bychan.core.basic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class EndTokenTest {

    @Test(expected = UnsupportedOperationException.class)
    public void toTokenNotSupported() {
        EndToken.get().toLexeme(mock(LexingMatch.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPatternNotSupported() {
        EndToken.get().getMatcher();
    }

    @Test
    public void isIncluded() {
        assertTrue(EndToken.get().keepAfterLexing());
    }

}