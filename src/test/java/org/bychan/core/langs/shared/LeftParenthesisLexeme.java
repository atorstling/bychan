package org.bychan.core.langs.shared;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LeftParenthesisLexeme<N> implements Lexeme<N> {

    @NotNull
    private final LexingMatch match;

    public LeftParenthesisLexeme(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<N> getNud() {
        return (left, parser) -> {
            N expression = parser.parseExpression(left, 0);
            parser.swallow(RightParenthesisToken.<N>get());
            return expression;
        };
    }

    @Nullable
    @Override
    public LedParseAction<N> getLed() {
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
    public Token<N> getToken() {
        return LeftParenthesisToken.get();
    }


    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
