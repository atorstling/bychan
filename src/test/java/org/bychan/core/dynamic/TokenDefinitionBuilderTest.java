package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TokenDefinitionBuilderTest {

    @Test(expected = IllegalStateException.class)
    public void getKeyTooEarly() {
        TokenDefinitionOwner owner = mock(TokenDefinitionOwner.class);
        //noinspection unchecked
        TokenDefinitionBuilder b = new TokenDefinitionBuilder<>(owner);
        b.getKey();
    }

    @Test
    public void getKeyAfterNameSet() {
        TokenDefinitionOwner owner = mock(TokenDefinitionOwner.class);
        //noinspection unchecked
        TokenDefinitionBuilder b = new TokenDefinitionBuilder<>(owner);
        b.named("hello");
        assertEquals(new TokenKey("hello"), b.getKey());
    }

}