package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A combined definition of a token type and token
 *
 * @param <N>
 */
public class TokenDefinition<N extends AstNode> {
    @NotNull
    private final String pattern;
    @Nullable
    private final PrefixAstBuilder<N> prefixBuilder;
    @Nullable
    private final InfixAstBuilder<N> infixBuilder;
    @NotNull
    private final String tokenTypeName;
    private final boolean filterOutBeforeParsing;

    public TokenDefinition(@NotNull final String pattern, @Nullable final PrefixAstBuilder<N> prefixBuilder, @Nullable final InfixAstBuilder<N> infixBuilder, @NotNull final String tokenTypeName, boolean filterOutBeforeParsing) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
        this.tokenTypeName = tokenTypeName;
        this.filterOutBeforeParsing = filterOutBeforeParsing;
    }

    @NotNull
    public String getPattern() {
        return pattern;
    }

    public boolean shouldFilterOutBeforeParsing() {
        return filterOutBeforeParsing;
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
