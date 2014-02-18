package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;

interface ParserStrategy<N> {
    @NotNull
    N parse(@Nullable N previous, @NotNull ArrayDeque<Token<N>> tokens, @NotNull PrattParser<N> parser);
}
