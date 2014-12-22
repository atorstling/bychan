package com.torstling.bychan.generic;

import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenParserCallback;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RightParenthesisToken<N> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public RightParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public N prefixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        throw new IllegalStateException("Cannot use right paranthesis as prefix to subExpression");
    }

    @NotNull
    public N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
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
