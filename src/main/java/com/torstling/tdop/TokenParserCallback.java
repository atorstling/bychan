package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public interface TokenParserCallback {
    @NotNull
    Node expression(int rightBindingPower);
}
