package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

public class LanguageBuilder2<N extends AstNode, S> {
    @NotNull
    private final LanguageBuilder<N,S> delegate;

    public LanguageBuilder2() {
        this.delegate = new LanguageBuilder<>();
    }

    @NotNull
    public LanguageBuilder2<N, S> addToken(@NotNull final TokenDefinition<N, S> token) {
        delegate.addToken(token);
        return this;
    }

    @NotNull
    public LanguageBuilder2<N, S> newLevel() {
        delegate.newLowerPriorityLevel();
        return this;
    }

    @NotNull
    public Language<N,S> completeLanguage() {
        return delegate.completeLanguage();
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N, S> startToken() {
        return new WrappedTokenDefinitionBuilder<N, S>(this, delegate.newToken());
    }
}
