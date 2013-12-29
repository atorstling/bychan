package com.torstling.tdop.generic;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class RightParenthesisToken<N extends AstNode> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public RightParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public N prefixParse(@NotNull N parent, @NotNull TokenParserCallback<N> parser) {
        throw new IllegalStateException("Cannot use right parenthesis as prefix to expression");
    }

    @NotNull
    public N infixParse(N parent, @NotNull N left, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 0;
    }

    public String toString() {
        return ")";
    }


    @Override
    @NotNull
    public TokenType<N> getType() {
        return RightParenthesisTokenType.get();
    }


    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
