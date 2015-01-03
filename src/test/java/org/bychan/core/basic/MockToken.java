package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

class MockToken implements Token<Object> {
    @NotNull
    @Override
    public Lexeme<Object> toLexeme(@NotNull LexingMatch match) {
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
