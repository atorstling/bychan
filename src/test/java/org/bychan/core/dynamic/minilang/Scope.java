package org.bychan.core.dynamic.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Scope {
    @Nullable
    VariableDefNode find(@NotNull final String name);

    void put(@NotNull final String name, @NotNull final VariableDefNode node);
}
