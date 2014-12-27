package org.bychan.generic;

import org.bychan.core.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LeftParenthesisToken<N> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public LeftParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public PrefixParseAction<N> getPrefixParser() {
        return (previous, parser) -> {
            N expression = parser.parseExpression(previous, 0);
            parser.swallow(RightParenthesisTokenType.<N>get());
            return expression;
        };
    }

    @Nullable
    @Override
    public InfixParseAction<N> getInfixParser() {
        return null;
    }

    public int leftBindingPower() {
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
