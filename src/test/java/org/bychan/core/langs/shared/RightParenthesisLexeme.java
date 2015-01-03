package org.bychan.core.langs.shared;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RightParenthesisLexeme<N> implements Lexeme<N> {

    @NotNull
    private final LexingMatch match;

    public RightParenthesisLexeme(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<N> getPrefixParser() {
        return null;
    }

    @Nullable
    @Override
    public LedParseAction<N> getInfixParser() {
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
    public Token<N> getToken() {
        return RightParenthesisToken.get();
    }


    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
