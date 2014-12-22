package com.torstling.bychan.fluid;

import com.torstling.bychan.core.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FluidParserCallback<N> {
    /**
     * Parse the directly following tokens an an expression. Proceed while tokens
     * with higher priority than the current token are found.
     * @param previous passed to the sub-expression parser as the node found directly before. If you
     *                 have performed any custom parsing in your handler you should pass in the directly leading
     *                 node before the sub-expression which you are trying to parse.
     */
    @NotNull
    N subExpression(@Nullable final N previous);
    /**
     * Same as {@link #subExpression(Object)}, but with {@link #subExpression(Object)::previous} set to be the
     * same {@code previous} as the passed into the handler which performs this call.
     * This makes sense if you haven't done any custom parsing in your handler.
     */
    @NotNull
    N subExpression();

    /**
     * Parse a single token of the type indicated by the token definition passed in.
     */
    @NotNull
    Token<N> expectSingleToken(TokenDefinition<N> tokenTypeDefinition);

    boolean nextIs(@NotNull final TokenDefinition<N> tokenTypeDefinition);

    @NotNull
    N parseSingleToken(N previous, TokenDefinition<N> tokenDefinition);
}
