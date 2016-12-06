package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TokenDefinitionBuilderTest {
    @Test
    public void getKeyAfterNameSet() {
        TokenDefinitionOwner owner = mock(TokenDefinitionOwner.class);
        //noinspection unchecked
        TokenDefinitionBuilder b = new TokenDefinitionBuilder<>(owner);
        b.named("hello");
        assertEquals("hello", b.getTokenName());
    }

}