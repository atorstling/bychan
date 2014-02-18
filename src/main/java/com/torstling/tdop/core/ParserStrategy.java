package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

interface ParserStrategy<N> {
    @NotNull
    N parse(@NotNull N previous, @NotNull ArrayDeque<Token<N>> tokens, @NotNull PrattParser<N> parser);
}
