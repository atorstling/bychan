package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

interface ParserStrategy<N, S> {
    @NotNull
    N parse(ArrayDeque<Token<N, S>> tokens, PrattParser<N, S> parser);
}
