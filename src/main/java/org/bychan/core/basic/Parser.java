package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface to the parser, which the tokens can use to
 * continue the parsing
 */
public interface Parser<N> {
    /**
     * Parse the directly following tokens an an expression. Proceed while tokens
     * with higher priority than given binding power are found.
     *
     * @param left passed to the sub-expression parser as the node found directly before. If you
     *             have performed any custom parsing in your handler you should pass in the directly leading
     *             node before the sub-expression which you are trying to parse.
     */
    @NotNull
    N parseExpression(@Nullable N left, int rightBindingPower);

    /**
     * Swallow a token of the specified token.
     */
    @NotNull
    Lexeme<N> swallow(@NotNull Token<N> token);

    @NotNull
    Lexeme<N> peek();

    /**
     * Call nud of the given lexeme, abort with a nice exception if the lexeme doesn't support nud parsing
     */
    @NotNull
    N nud(@NotNull Lexeme<N> lexeme, @Nullable N left);

    @NotNull
    ParsingPosition getParsingPosition();

    @NotNull
    Lexeme<N> next();

    <S> S abort(String message);
}
