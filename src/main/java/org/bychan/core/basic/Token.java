package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A token in the lexing stream.
 *
 *
 */
public interface Token<N> {
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
    TokenType<N> getType();

    /**
     * @return the lexing match which this token originated from
     */
    @NotNull
    LexingMatch getMatch();
}
