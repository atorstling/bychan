package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface TokenType<N extends Node> {
    @NotNull
    Token<N> toToken(@NotNull final LexingMatch match);
    @NotNull
    String getPattern();
}
