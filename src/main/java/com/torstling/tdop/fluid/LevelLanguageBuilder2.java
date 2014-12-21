package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class LevelLanguageBuilder2<N> {
    @NotNull
    private final LevelLanguageBuilder<N> delegate;
    @NotNull
    private final LanguageBuilder2<N> previous;

    @NotNull
    public LevelLanguageBuilder2<N> addToken(@NotNull TokenDefinition<N> token) {
        delegate.addToken(token);
        return this;
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> startToken() {
        return new WrappedTokenDefinitionBuilder<>(this, new TokenDefinitionBuilder<>());
    }

    public LanguageBuilder2<N> endLevel() {
        delegate.endLevel();
        return previous;
    }

    public LevelLanguageBuilder2(@NotNull LevelLanguageBuilder<N> delegate, @NotNull LanguageBuilder2<N> previous) {
        this.delegate = delegate;
        this.previous = previous;
    }

    @NotNull
    public LevelLanguageBuilder2<N> newLevel() {
        return endLevel().newLowerPriorityLevel();
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> ńewToken() {
        return startToken();
    }

    @NotNull
    public Language<N> completeLanguage() {
        return delegate.completeLanguage();
    }
}
