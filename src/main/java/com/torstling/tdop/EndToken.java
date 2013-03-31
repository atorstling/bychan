package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class EndToken<N extends Node> implements Token<N> {

    @NotNull
    public N prefixParse(@NotNull final TokenParserCallback parser) {
        throw new IllegalStateException("Cannot parse end as expression");
    }

    @NotNull
    public N infixParse(@NotNull N left, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 0;
    }

    @Override
    public boolean isOfType(@NotNull TokenType<N> type) {
        return type.equals(EndTokenType.get());
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        throw new UnsupportedOperationException("Never really matched, always at end");
    }

    public String toString() {
        return ".";
    }
}
