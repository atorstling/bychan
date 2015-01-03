package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A lexeme in the lexing stream.
 *
 *
 */
public interface Lexeme<N> {
    @Nullable
    PrefixParseAction<N> getPrefixParser();

    @Nullable
    InfixParseAction<N> getInfixParser();


    /**
     * @return How strongly this token, when interpreted as an infix operator, binds to the previous argument.
     */
    int leftBindingPower();

    /**
     * Check the type of this token.
     */
    @NotNull
    Token<N> getToken();

    /**
     * @return the lexing match which this token originated from
     */
    @NotNull
    LexingMatch getMatch();
}
