package com.torstling.tdop.generic;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenParserCallback;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class RightParenthesisToken<N, S> implements Token<N, S> {

    @NotNull
    private final LexingMatch match;

    public RightParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public N prefixParse(@NotNull S parent, @NotNull TokenParserCallback<N, S> parser) {
        throw new IllegalStateException("Cannot use right parenthesis as prefix to expression");
    }

    @NotNull
    public N infixParse(S parent, @NotNull N left, @NotNull TokenParserCallback<N, S> parser) {
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
    public TokenType<N, S> getType() {
        return RightParenthesisTokenType.get();
    }


    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
