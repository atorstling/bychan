package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

public interface TokenFinder<N> {
    @NotNull
    Token<N> getToken(@NotNull final String name);
}
