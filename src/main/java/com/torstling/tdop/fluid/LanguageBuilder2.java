package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class LanguageBuilder2<N> {
    @NotNull
    private final LanguageBuilder<N> delegate;

    public LanguageBuilder2() {
        this.delegate = new LanguageBuilder<>();
    }

    @NotNull
    public LevelLanguageBuilder2<N> newLowerPriorityLevel() {
        return new LevelLanguageBuilder2<>(delegate.newLowerPriorityLevel(), this);
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
}
