package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TokenDefinition<N extends AstNode> {
    @NotNull
    private final String pattern;
    @Nullable
    private final PrefixAstBuilder<N> prefixBuilder;
    @Nullable
    private final InfixAstBuilder<N> infixBuilder;
    @NotNull
    private final String tokenTypeName;

    public TokenDefinition(@NotNull final String pattern, @Nullable final PrefixAstBuilder<N> prefixBuilder, @Nullable final InfixAstBuilder<N> infixBuilder, @NotNull final String tokenTypeName) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
        this.tokenTypeName = tokenTypeName;
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

    @NotNull
    public String getTokenTypeName() {
        return tokenTypeName;
    }
}
