package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;

public class FluidLanguageBuilder<N> {
    @NotNull
    private final LanguageBuilder<N> delegate;

    public FluidLanguageBuilder() {
        this.delegate = new LanguageBuilder<>();
    }

    @NotNull
    public FluidLevelLanguageBuilder<N> newLowerPriorityLevel() {
        return new FluidLevelLanguageBuilder<>(delegate.newLowerPriorityLevel(), this);
    }

    @NotNull
    public Language<N> completeLanguage() {
        return delegate.completeLanguage();
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> newLevelToken() {
        return newLowerPriorityLevel().startToken();
    }

    public TokenDefinitionBuilder<N> newToken() {
        return delegate.newToken();
    }

    @NotNull
    public FluidLanguageBuilder<N> named(@NotNull final String name) {
        delegate.named(name);
        return this;
    }
}
