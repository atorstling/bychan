package org.bychan.core.basic;

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
     * @return How strongly this lexeme, when interpreted as an infix operator (led), binds to the previous argument.
     */
    int leftBindingPower();

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
}