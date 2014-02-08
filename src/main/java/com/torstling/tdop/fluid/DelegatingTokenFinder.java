package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class DelegatingTokenFinder<N, S> implements TokenFinder<N, S> {
    @NotNull
    private TokenFinder<N, S> delegate;

    @NotNull
    @Override
    public GenericTokenType<N, S> getTokenTypeFor(@NotNull TokenDefinition<N, S> tokenDefinition) {
        return delegate.getTokenTypeFor(tokenDefinition);
    }

    public void setDelegate(@NotNull final TokenFinder<N, S> delegate) {
        this.delegate = delegate;
    }
}
