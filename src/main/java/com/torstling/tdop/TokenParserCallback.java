package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenParserCallback<N extends Node> {
    @NotNull
    N expression(int rightBindingPower);
    @NotNull
    LexingMatch swallow(@NotNull TokenType<N> tokenType);
}
