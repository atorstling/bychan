package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

interface DynamicTokenFinder<N> {
    @NotNull
    Token<N> getToken(@NotNull final TokenKey soughtKey);
}
