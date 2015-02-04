package org.bychan.core.basic;

import org.bychan.core.TokenMatcher;
import org.jetbrains.annotations.NotNull;

class MockToken implements Token<Object> {
    @NotNull
    @Override
    public Lexeme<Object> toLexeme(@NotNull LexingMatch match) {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public TokenMatcher getMatcher() {
        throw new IllegalStateException();
    }

    @Override
    public boolean keepAfterLexing() {
        throw new IllegalStateException();
    }
}
