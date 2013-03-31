package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class LeftParenthesisToken<N extends Node> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public LeftParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public N prefixParse(@NotNull TokenParserCallback<N> parser) {
        N expression = parser.expression(0);
        parser.swallow(RightParenthesisTokenType.<N>get());
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

    @Override
    public boolean isOfType(@NotNull TokenType<N> type) {
        return type.equals(LeftParenthesisTokenType.<N>get());
    }

    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
