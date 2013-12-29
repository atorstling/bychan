package com.torstling.tdop.generic;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class LeftParenthesisToken<N extends AstNode> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public LeftParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public N prefixParse(@NotNull N parent, @NotNull TokenParserCallback<N> parser) {
        N expression = parser.expression(parent, 0);
        parser.swallow(RightParenthesisTokenType.<N>get());
        return expression;
    }

    @NotNull
    public N infixParse(N parent, @NotNull N left, @NotNull TokenParserCallback<N> parser) {
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
