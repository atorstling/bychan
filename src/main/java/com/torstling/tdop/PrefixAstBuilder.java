package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface PrefixAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match, @NotNull ParserCallback2<N> callback);
}
