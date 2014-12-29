package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

interface TokenFinder<N> {
    @NotNull
    DynamicTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}