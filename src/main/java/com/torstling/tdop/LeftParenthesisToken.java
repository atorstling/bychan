package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class LeftParenthesisToken<N extends Node> implements Token<N> {

    @NotNull
    public N prefixParse(@NotNull TokenParserCallback<N> parser) {
        N expression = parser.expression(0);
        parser.swallow((Class) RightParenthesisToken.class);
        return expression;
    }

    @NotNull
    public N infixParse(@NotNull N left, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "(";
    }
}
