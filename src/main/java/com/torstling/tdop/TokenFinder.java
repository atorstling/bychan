package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface TokenFinder<N extends Node> {
    @NotNull
    DefinitionTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}
