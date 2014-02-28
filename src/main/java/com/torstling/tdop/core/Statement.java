package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface Statement<N> {
    @NotNull
    N parse(@Nullable N previous, @NotNull PrattParser<N> parser);
}
