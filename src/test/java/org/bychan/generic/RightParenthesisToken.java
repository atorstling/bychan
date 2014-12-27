package org.bychan.generic;

import org.bychan.core.LexingMatch;
import org.bychan.core.Token;
import org.bychan.core.TokenParserCallback;
import org.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RightParenthesisToken<N> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public RightParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Override
    public boolean supportsPrefixParsing() {
        return false;
    }

    @NotNull
    public N prefixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        throw new IllegalStateException("Cannot use right paranthesis as prefix to subExpression");
    }

    @Override
    public boolean supportsInfixParsing() {
        return false;
    }

    @NotNull
    public N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int leftBindingPower() {
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
