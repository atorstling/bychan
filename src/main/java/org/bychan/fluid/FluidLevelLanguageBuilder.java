package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;

public class FluidLevelLanguageBuilder<N> {
    @NotNull
    private final LevelLanguageBuilder<N> delegate;
    @NotNull
    private final FluidLanguageBuilder<N> previous;

    @NotNull
    public FluidLevelLanguageBuilder<N> addToken(@NotNull TokenDefinition<N> token) {
        delegate.addToken(token);
        return this;
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> startToken() {
        return new WrappedTokenDefinitionBuilder<>(this, new TokenDefinitionBuilder<>());
    }

    public FluidLanguageBuilder<N> endLevel() {
        delegate.endLevel();
        return previous;
    }

    public FluidLevelLanguageBuilder(@NotNull LevelLanguageBuilder<N> delegate, @NotNull FluidLanguageBuilder<N> previous) {
        this.delegate = delegate;
        this.previous = previous;
    }

    @NotNull
    public FluidLevelLanguageBuilder<N> newLevel() {
        return endLevel().newLowerPriorityLevel();
    }

    @NotNull
    public Language<N> completeLanguage() {
        return delegate.completeLanguage();
    }
}
