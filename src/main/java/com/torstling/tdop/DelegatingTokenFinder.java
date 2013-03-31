package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class DelegatingTokenFinder implements TokenFinder {
    @NotNull
    private TokenFinder delegate;

    @Override
    public DefinitionTokenType getTokenTypeFor(@NotNull TokenDefinition tokenDefinition) {
        return delegate.getTokenTypeFor(tokenDefinition);
    }

    public void setDelegate(@NotNull final TokenFinder delegate) {
        this.delegate = delegate;
    }
}
