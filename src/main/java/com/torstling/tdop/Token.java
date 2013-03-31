package com.torstling.tdop;

import com.sun.istack.internal.NotNull;
import sun.font.AttributeValues;

interface Token<N extends Node> {
    @NotNull
    N prefixParse(@NotNull TokenParserCallback<N> parser);
    @NotNull
    N infixParse(@NotNull N left, @NotNull TokenParserCallback<N> parser);
    /**
     * @return How strongly this token, when interpreted as an infix operator, binds to the left argument.
     */
    int infixBindingPower();
    @NotNull
    TokenType<N> getType();
}
