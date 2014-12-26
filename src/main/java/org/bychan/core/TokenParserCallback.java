package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface to the parser, which the nodes can use to
 * continue the parsing
 */
public interface TokenParserCallback<N> {
    @NotNull
    N parseExpression(@Nullable N previous, int powerFloor);

    /**
     * Swallow a token of the specified type.
     */
    @NotNull
    Token<N> swallow(@NotNull TokenType<N> type);

    @NotNull
    Token<N> peek();

    @NotNull
    Token<N> pop();

    @NotNull
    ParsingPosition getParsingPosition();
}
