package org.bychan.core.basic;

import org.bychan.core.dynamic.TokenMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A lexeme in the lexing stream.
 */
public interface Lexeme<N> {
    @Nullable
    NudParseAction<N> getNud();

    @Nullable
    LedParseAction<N> getLed();
    /**
     * @return The left binding power of this lexeme.
     * How strongly this lexeme, when interpreted as an infix operator (led), binds to the left argument.
     */
    int lbp();

    /**
     * Get the token of this lexeme.
     */
    @NotNull
    Token<N> getToken();

    /**
     * @return the lexing match which this lexeme originated from
     */
    @NotNull
    LexingMatch getMatch();

    @NotNull
    default String text() {
        return getMatch().getText();
    }

    @NotNull
    default TokenMatcher getMatcher() {
        return getMatch().getMatcher();
    }

    @NotNull
    default Object getLexerValue() {
        return getMatch().getLexerValue();
    }

    @NotNull
    default String group(int i) {
        return getMatch().group(i);
    }

    default boolean isA(String tokenName) {
        return getToken().getName().equals(tokenName);
    }
}
