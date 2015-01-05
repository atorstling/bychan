package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

interface DynamicTokenFinder<N> {
    @NotNull
    DynamicToken<N> getToken(@NotNull final String soughtName);
}
