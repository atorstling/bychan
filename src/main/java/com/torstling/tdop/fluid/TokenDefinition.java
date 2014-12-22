package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * A combined definition of a token type and token
 *
 * @param <N>
 */
public class TokenDefinition<N> {
    @NotNull
    private final Pattern pattern;
    @Nullable
    private final PrefixAstBuilder<N> prefixBuilder;
    @Nullable
    private final InfixAstBuilder<N> infixBuilder;
    @NotNull
    private final String tokenTypeName;
    private final boolean parse;

    public TokenDefinition(@NotNull final Pattern pattern, @Nullable final PrefixAstBuilder<N> prefixBuilder, @Nullable final InfixAstBuilder<N> infixBuilder, @NotNull final String tokenTypeName, boolean parse) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
        this.tokenTypeName = tokenTypeName;
        this.parse = parse;
    }

    @NotNull
    public Pattern getPattern() {
        return pattern;
    }

    public boolean parse() {
        return parse;
    }

    @Override
    public String toString() {
        return tokenTypeName;
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
