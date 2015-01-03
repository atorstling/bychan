package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class EndToken<N> implements Token<N> {

    private static final EndToken INSTANCE = new EndToken<>();

    @NotNull
    @Override
    public Lexeme<N> toLexeme(@NotNull LexingMatch match) {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @Override
    public boolean include() {
        return true;
    }

    @NotNull
    public static <N> EndToken<N> get() {
        //noinspection unchecked
        return (EndToken<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
