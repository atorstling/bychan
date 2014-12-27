package org.bychan.core.langs.shared;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RightParenthesisToken<N> implements Token<N> {

    @NotNull
    private final LexingMatch match;

    public RightParenthesisToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public PrefixParseAction<N> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public InfixParseAction<N> getInfixParser() {
        return null;
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
