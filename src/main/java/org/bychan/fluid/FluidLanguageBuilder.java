package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;

public class FluidLanguageBuilder<N> {
    @NotNull
    private final LanguageBuilder<N> delegate;

    public FluidLanguageBuilder() {
        this.delegate = new LanguageBuilder<>();
    }

    @NotNull
    public Language<N> completeLanguage() {
        return delegate.completeLanguage();
    }

    public TokenDefinitionBuilder<N> newToken() {
        return delegate.newToken();
    }

    @NotNull
    public FluidLanguageBuilder<N> named(@NotNull final String name) {
        delegate.named(name);
        return this;
    }

    public TokenDefinitionBuilder<N> startToken() {
        return delegate.startToken();
    }

    @NotNull
    public FluidLanguageBuilder<N> endLevel() {
        return this;
    }

    @NotNull
    public LanguageBuilder<N> getDelegate() {
        return delegate;
    }

    @NotNull
    public FluidLanguageBuilder<N> addToken(TokenDefinition<N> tokenDefinition) {
        delegate.addToken(tokenDefinition);
        return this;
    }

    public FluidLanguageBuilder<N> newLevel() {
        return this;
    }
}
