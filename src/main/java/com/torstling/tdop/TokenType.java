package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenType<N extends Node> {
    @NotNull
    Token<N> toToken(@NotNull final LexingMatch match);
    @NotNull
    String getPattern();
}
