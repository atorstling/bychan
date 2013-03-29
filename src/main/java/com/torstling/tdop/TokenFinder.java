package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenFinder<N extends Node> {
    @NotNull
    DefinitionTokenType<N> getTokenFor(@NotNull final TokenDefinition<N> definition);
}
