package com.torstling.tdop.fluid;

import com.torstling.tdop.core.Token;
import org.jetbrains.annotations.NotNull;

public interface ParserCallback2<N> {
    /**
     * Parse the trailing expression. Continue as long as only tokens
     * with higher priority than the current token are found.
     */
    @NotNull
    N expression(@NotNull final N previous);

    /**
     * Parse a single token of the type indicated by the token definition passed in.
     */
    @NotNull
    Token<N> expectSingleToken(TokenDefinition<N> tokenTypeDefinition);

    boolean nextIsNot(@NotNull final TokenDefinition<N> tokenTypeDefinition);
}
