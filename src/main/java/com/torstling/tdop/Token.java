package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;
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
    boolean isOfType(@NotNull TokenType<N> type);
    @NotNull
    LexingMatch getMatch();
}
