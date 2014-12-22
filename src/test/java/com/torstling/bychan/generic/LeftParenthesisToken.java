package com.torstling.bychan.generic;

import com.torstling.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LeftParenthesisToken<N> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public LeftParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public N prefixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        N expression = parser.tryParse(previous, new Expression<>(0)).getRootNode();
        parser.swallow(RightParenthesisTokenType.<N>get());
        return expression;
    }

    @NotNull
    public N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "(";
    }

    @Override
    @NotNull
    public TokenType<N> getType() {
        return LeftParenthesisTokenType.get();
    }


    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
