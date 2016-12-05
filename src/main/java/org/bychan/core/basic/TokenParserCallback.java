package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface to the parser, which the tokens can use to
 * continue the parsing
 */
public interface TokenParserCallback<N> {
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
