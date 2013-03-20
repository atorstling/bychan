package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenParserCallback<N extends Node> {
    @NotNull
    N expression(int rightBindingPower);
    void swallow(Class<? extends Token<N>> token);
}
