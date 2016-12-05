package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface to the parser, which the tokens can use to
 * continue the parsing
 */
public interface TokenParserCallback<N> {
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

    @NotNull
    N nud(@Nullable N left, @NotNull Lexeme<N> lexeme);

    @NotNull
    ParsingPosition getParsingPosition();

    @NotNull
    Lexeme<N> next();

    <S> S abort(String message);
}
