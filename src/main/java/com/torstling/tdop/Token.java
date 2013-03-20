package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

interface Token<N extends Node> {
    N prefixParse(@NotNull TokenParserCallback<N> parser);
    N infixParse(N left, TokenParserCallback<N> parser);
    /**
     * @return How strongly this token, when interpreted as an infix operator, binds to the left argument.
     */
    int infixBindingPower();
}
