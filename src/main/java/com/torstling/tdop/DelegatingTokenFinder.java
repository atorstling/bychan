package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class DelegatingTokenFinder<N extends Node> implements TokenFinder<N> {
    @NotNull
    private TokenFinder<N> delegate;

    @NotNull
    @Override
    public DefinitionTokenType<N> getTokenTypeFor(@NotNull TokenDefinition<N> tokenDefinition) {
        return delegate.getTokenTypeFor(tokenDefinition);
    }

    public void setDelegate(@NotNull final TokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
