package org.bychan.core.dynamic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LanguageBuilderTest {

    @Test
    public void noNameIsAcceptable() {
        new LanguageBuilder<>().completeLanguage();
    }

    @Test
    public void noPatternOnTokenIsError() {
        LanguageBuilder<Object> lb = new LanguageBuilder<>();
        try {
            lb.newToken().buildAndAdd();
            fail("exception expected");
        } catch (IllegalStateException e) {
            assertEquals("No matching pattern has been set", e.getMessage());
        }
    }

    @Test
    public void noNameOnTokenResultsInDefault() {
        LanguageBuilder<Object> lb = new LanguageBuilder<>();
        TokenDefinition<Object> tokenDefinition = lb.newToken().matchesString("[0-9]+").buildAndAdd();
        lb.completeLanguage();
        assertEquals("token1", tokenDefinition.getTokenTypeName());
    }
}