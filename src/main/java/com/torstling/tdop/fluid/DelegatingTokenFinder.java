package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class DelegatingTokenFinder<N> implements TokenFinder<N> {
    @NotNull
    private TokenFinder<N> delegate;

    @NotNull
    @Override
    public GenericTokenType<N> getTokenTypeFor(@NotNull TokenDefinition<N> tokenDefinition) {
        return delegate.getTokenTypeFor(tokenDefinition);
    }

    public void setDelegate(@NotNull final TokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
