package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * A token type defines how to make certain types of tokens.
 * A pattern defines which string segments to match during lexing,
 * and a factory method makes tokens out of the resulting matches.
 *
 * @param <N>
 */
public interface TokenType<N extends AstNode,S> {
    @NotNull
    Token<N,S> toToken(@NotNull final LexingMatch match);

    @NotNull
    Pattern getPattern();

    /**
     * @return whether the lexer should ignore this token type or not.
     * Ignored token types will not be part of the token stream which the lexer produces, but they
     * might still be useful to ignore certain patterns (such as whitespace and comments).
     */
    boolean shouldSkip();
}
