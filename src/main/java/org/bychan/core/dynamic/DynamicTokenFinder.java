package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

interface DynamicTokenFinder<N> {
    @NotNull
    DynamicTokenType<N> getTokenTypeFor(@NotNull final TokenKey soughtKey);
}
