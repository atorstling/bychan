package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

interface ParserStrategy<N> {
    @NotNull
    N parse(ArrayDeque<Token<N>> tokens, PrattParser<N> parser);
}
