package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

public class LanguageBuilder2<N extends AstNode> {
    @NotNull
    private final LanguageBuilder<N> delegate;

    public LanguageBuilder2() {
        this.delegate = new LanguageBuilder<>();
    }

    @NotNull
    public LanguageBuilder2<N> addToken(@NotNull final TokenDefinition<N> token) {
        delegate.addToken(token);
        return this;
    }

    @NotNull
    public LanguageBuilder2<N> newLevel() {
        delegate.newLevel();
        return this;
    }

    @NotNull
    public Language<N> completeLanguage() {
        return delegate.completeLanguage();
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> startToken() {
        return new WrappedTokenDefinitionBuilder<>(this, delegate.newToken());
    }
}
