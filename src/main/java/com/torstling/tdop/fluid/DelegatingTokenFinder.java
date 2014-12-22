package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelegatingTokenFinder<N> implements TokenFinder<N> {
    @Nullable
    private TokenFinder<N> delegate;

    @NotNull
    @Override
    public GenericTokenType<N> getTokenTypeFor(@NotNull TokenDefinition<N> tokenDefinition) {
        assert delegate != null;
        return delegate.getTokenTypeFor(tokenDefinition);
    }

    public void setDelegate(@SuppressWarnings("NullableProblems") @NotNull final TokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
