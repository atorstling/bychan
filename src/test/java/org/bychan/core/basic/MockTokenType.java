package org.bychan.core.basic;

import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.bychan.core.basic.TokenType;
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
