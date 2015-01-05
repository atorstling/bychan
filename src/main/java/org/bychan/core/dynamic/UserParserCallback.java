package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The parser interface used in the dynamic API
 */
public interface UserParserCallback<N> {
    /**
     * Parse the directly following tokens an an expression. Proceed while tokens
     * with higher priority than given binding power are found.
     *
     * @param previous passed to the sub-expression parser as the node found directly before. If you
     *                 have performed any custom parsing in your handler you should pass in the directly leading
     *                 node before the sub-expression which you are trying to parse.
     */
    @NotNull
    N subExpression(@Nullable final N previous, int leftBindingPower);

    /**
     * Parse the directly following tokens an an expression. Proceed while tokens
     * with higher priority than the previous token are found.
     *
     * @param previous passed to the sub-expression parser as the node found directly before. If you
     *                 have performed any custom parsing in your handler you should pass in the directly leading
     *                 node before the sub-expression which you are trying to parse.
     */
    @NotNull
    N subExpression(@Nullable final N previous);

    /**
     * Parse a single lexeme of the token identified by the given key.
     */
    @NotNull
    Lexeme<N> expectSingleLexeme(@NotNull TokenKey tokenKey);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean nextIs(@NotNull TokenKey tokenKey);

    @NotNull
    N parseSingleToken(N previous, @NotNull TokenKey tokenKey);
}
