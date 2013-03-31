package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface InfixAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match, @NotNull N left, @NotNull ParserCallback2<N> parser);
}
