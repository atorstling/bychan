package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * A token type defines how to make certain types of tokens.
 * A pattern defines which string segments to match during lexing,
 * and a factory method makes tokens out of the resulting matches.
 * @param <N>
 */
public interface TokenType<N extends AstNode> {
    @NotNull
    Token<N> toToken(@NotNull final LexingMatch match);
    @NotNull
    String getPattern();
}
