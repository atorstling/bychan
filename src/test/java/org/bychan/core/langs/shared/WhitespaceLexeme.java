package org.bychan.core.langs.shared;

import org.bychan.core.basic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WhitespaceLexeme<N> implements Lexeme<N> {
    private final WhitespaceToken<N> token;
    @NotNull
    private final LexingMatch match;

    public WhitespaceLexeme(WhitespaceToken<N> token, @NotNull final LexingMatch match) {
        this.token = token;
        this.match = match;
    }

    @Nullable
    @Override
    public NudParseAction<N> getNud() {
        return null;
    }

    @Nullable
    @Override
    public LedParseAction<N> getLed() {
        return null;
    }

    @Override
    public int lbp() {
        return 0;
    }

    @NotNull
    @Override
    public Token<N> getToken() {
        return token;
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
