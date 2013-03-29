package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface InfixAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match, @NotNull N left, @NotNull ParserCallback2<N> parser);
}
