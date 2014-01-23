package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

public class LevelLanguageBuilder2<N extends AstNode, S> {
    private LevelLanguageBuilder<N, S> delegate;
    private LanguageBuilder2<N, S> parent;

    public LevelLanguageBuilder<N,S> startToken(@NotNull TokenDefinition<N, S> token) {
        return delegate.addToken(token);
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N, S> startToken() {
        return new WrappedTokenDefinitionBuilder<N, S>(this, new TokenDefinitionBuilder<N, S>());
    }

    public LanguageBuilder2<N,S> endLevel() {
        delegate.endLevel();
        return parent;
    }

    public LevelLanguageBuilder2(LevelLanguageBuilder<N, S> delegate, LanguageBuilder2<N, S> parent) {
        this.delegate = delegate;
        this.parent = parent;
    }
}
