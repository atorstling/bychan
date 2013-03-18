package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenParserCallback {
    @NotNull
    CalculatorNode expression(int rightBindingPower);
    void swallow(Class<? extends Token> token);
}
