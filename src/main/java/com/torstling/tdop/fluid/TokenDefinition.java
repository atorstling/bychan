package com.torstling.tdop.fluid;

import com.torstling.tdop.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TokenDefinition<N extends AstNode> {
    @NotNull
    private final String pattern;
    @Nullable
    private final PrefixAstBuilder<N> prefixBuilder;
    @Nullable
    private final InfixAstBuilder<N> infixBuilder;

    public TokenDefinition(@NotNull final String pattern, @Nullable final PrefixAstBuilder<N> prefixBuilder, @Nullable final InfixAstBuilder<N> infixBuilder) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
    }

    @NotNull
    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }

    @Nullable
    public PrefixAstBuilder<N> getPrefixBuilder() {
        return prefixBuilder;
    }

    @Nullable
    public InfixAstBuilder<N> getInfixBuilder() {
        return infixBuilder;
    }
}
