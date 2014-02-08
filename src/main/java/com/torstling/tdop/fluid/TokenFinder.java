package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

interface TokenFinder<N, S> {
    @NotNull
    GenericTokenType<N, S> getTokenTypeFor(@NotNull final TokenDefinition<N, S> definition);
}
