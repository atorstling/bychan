package org.bychan.core.basic;

import org.bychan.core.dynamic.TokenMatcher;
import org.jetbrains.annotations.NotNull;

public class EndToken<N> implements Token<N> {

    private static final EndToken INSTANCE = new EndToken<>();

    @NotNull
    public static <N> EndToken<N> get() {
        //noinspection unchecked
        return (EndToken<N>) INSTANCE;
    }

    @NotNull
    @Override
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @NotNull
    @Override
    public TokenMatcher getMatcher() {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
