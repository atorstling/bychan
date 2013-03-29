package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class DelegatingTokenFinder implements TokenFinder {
    @NotNull
    private TokenFinder delegate;

    @Override
    public DefinitionTokenType getTokenFor(@NotNull TokenDefinition tokenDefinition) {
        return delegate.getTokenFor(tokenDefinition);
    }

    public void setDelegate(@NotNull final TokenFinder delegate) {
        this.delegate = delegate;
    }
}
