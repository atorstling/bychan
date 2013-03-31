package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface StandaloneAstBuilder<N extends Node> {
    N build(@NotNull LexingMatch match);
}
