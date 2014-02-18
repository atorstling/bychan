package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * An interface to the parser, which the nodes can use to
 * continue the parsing
 */
public interface TokenParserCallback<N> {
    @NotNull
    ParseResult<N> tryParse(@NotNull N previous, @NotNull ParserStrategy<N> strategy);

    /**
     * Swallow a token of the specified type.
     */
    @NotNull
    Token<N> swallow(@NotNull TokenType<N> type);

    @NotNull
    Token<N> peek();
}
