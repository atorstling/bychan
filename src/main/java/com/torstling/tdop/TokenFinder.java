package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenFinder<N extends Node> {
    @NotNull
    DefinitionTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}
