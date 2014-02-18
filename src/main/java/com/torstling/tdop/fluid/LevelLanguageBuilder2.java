package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

public class LevelLanguageBuilder2<N> {
    @NotNull
    private final LevelLanguageBuilder<N> delegate;
    @NotNull
    private final LanguageBuilder2<N> previous;

    public LevelLanguageBuilder<N> startToken(@NotNull TokenDefinition<N> token) {
        return delegate.addToken(token);
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
}
