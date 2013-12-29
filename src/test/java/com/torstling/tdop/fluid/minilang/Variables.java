package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Variables {
    @Nullable
    VariableNode find(@NotNull final String name);

    void put(@NotNull final String name, @NotNull final VariableNode node);
}
