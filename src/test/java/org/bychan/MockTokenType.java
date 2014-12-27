package org.bychan;

import org.bychan.core.LexingMatch;
import org.bychan.core.Token;
import org.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

class MockTokenType implements TokenType<Object> {
    @NotNull
    @Override
    public Token<Object> toToken(@NotNull LexingMatch match) {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        throw new IllegalStateException();
    }

    @Override
    public boolean include() {
        throw new IllegalStateException();
    }
}
