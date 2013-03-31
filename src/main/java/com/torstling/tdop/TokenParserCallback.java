package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenParserCallback<N extends Node> {
    @NotNull
    N expression(int rightBindingPower);
    @NotNull
    Token<N> swallow(@NotNull TokenType<N> type);
}
