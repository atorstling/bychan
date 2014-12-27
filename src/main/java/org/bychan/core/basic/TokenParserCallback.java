package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface to the parser, which the tokens can use to
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

    @NotNull
    N prefixParse(@Nullable N previous, @NotNull Token<N> token);
}
