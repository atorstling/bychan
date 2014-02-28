package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/28/14.
 */
class TokenMatchResult<N> {
    @NotNull
    private final LexingMatch match;
    @NotNull
    private final TokenType<N> tokenType;
    @NotNull
    private final Token<N> token;

    TokenMatchResult(@NotNull LexingMatch match, TokenType<N> tokenType, @NotNull Token<N> token) {
        this.match = match;
        this.tokenType = tokenType;
        this.token = token;
    }

    @NotNull
    public Token<N> getToken() {
        return token;
    }

    @NotNull
    public LexingMatch getMatch() {
        return match;
    }

    @NotNull
    public TokenType<N> getTokenType() {
        return tokenType;
    }
}
