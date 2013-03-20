package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class RightParenthesisToken<N extends Node> implements Token<N> {

    public N prefixParse(@NotNull TokenParserCallback<N> parser) {
        throw new IllegalStateException("Cannot use right parenthesis as prefix to expression");
    }

    public N infixParse(N left, TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 0;
    }

    public String toString() {
        return ")";
    }
}
