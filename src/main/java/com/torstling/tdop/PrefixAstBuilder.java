package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface PrefixAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match, @NotNull ParserCallback2<N> callback);
}
