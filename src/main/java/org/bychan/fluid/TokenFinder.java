package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;

interface TokenFinder<N> {
    @NotNull
    GenericTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}
