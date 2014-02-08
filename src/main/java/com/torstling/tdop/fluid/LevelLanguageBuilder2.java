package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class LevelLanguageBuilder2<N, S> {
    @NotNull
    private final LevelLanguageBuilder<N, S> delegate;
    @NotNull
    private final LanguageBuilder2<N, S> parent;

    public LevelLanguageBuilder<N, S> startToken(@NotNull TokenDefinition<N, S> token) {
        return delegate.addToken(token);
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N, S> startToken() {
        return new WrappedTokenDefinitionBuilder<>(this, new TokenDefinitionBuilder<>());
    }

    public LanguageBuilder2<N, S> endLevel() {
        delegate.endLevel();
        return parent;
    }

    public LevelLanguageBuilder2(@NotNull LevelLanguageBuilder<N, S> delegate, @NotNull LanguageBuilder2<N, S> parent) {
        this.delegate = delegate;
        this.parent = parent;
    }
}
