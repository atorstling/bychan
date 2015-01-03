package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * A token defines how to make certain types of lexemes.
 * A pattern defines which string segments to match during lexing,
 * and a factory method makes lexemes out of the resulting matches.
 *
 */
public interface Token<N> {
    @NotNull
    Lexeme<N> toLexeme(@NotNull final LexingMatch match);

    @NotNull
    Pattern getPattern();

    /**
     * @return whether the lexer should ignore this token or not.
     * Ignored token will not be part of the lexeme stream which the lexer produces, but they
     * might still be useful to ignore certain patterns (such as whitespace and comments).
     */
    boolean include();

    @NotNull
    default String getName() {
        return getClass().getSimpleName();
    }
}
