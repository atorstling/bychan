package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class LanguageBuilder2<N, S> {
    @NotNull
    private final LanguageBuilder<N, S> delegate;

    public LanguageBuilder2() {
        this.delegate = new LanguageBuilder<>();
    }

    @NotNull
    public LevelLanguageBuilder2<N, S> newLevel() {
        return new LevelLanguageBuilder2<>(delegate.newLowerPriorityLevel(), this);
    }

    @NotNull
    public Language<N, S> completeLanguage() {
        return delegate.completeLanguage();
    }
}
