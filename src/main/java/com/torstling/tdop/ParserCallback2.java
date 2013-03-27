package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface ParserCallback2<N extends Node> {
    /**
     * Parse the trailing expression. Continue as long as only tokens
     * with higher priority than the current token are found.
     */
    @NotNull
    N expression();

    @NotNull
    LexingMatch expect(TokenDefinition<N> tokenD);
}
