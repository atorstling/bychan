package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface StandaloneAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match);
}
