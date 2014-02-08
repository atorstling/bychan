package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * An interface to the parser, which the nodes can use to
 * continue the parsing
 */
public interface TokenParserCallback<N extends AstNode, S> {
    @NotNull
    ParseResult<N> tryParse(@NotNull ParserStrategy<N, S> strategy);

    /**
     * Swallow a token of the specified type.
     */
    @NotNull
    Token<N, S> swallow(@NotNull TokenType<N,S> type);

    @NotNull
    Token<N, S> peek();
}
