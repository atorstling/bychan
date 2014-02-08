package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * A combined definition of a token type and token
 *
 * @param <N>
 */
public class TokenDefinition<N extends AstNode, S> {
    @NotNull
    private final Pattern pattern;
    @Nullable
    private final PrefixAstBuilder<N, S> prefixBuilder;
    @Nullable
    private final InfixAstBuilder<N, S> infixBuilder;
    @NotNull
    private final String tokenTypeName;
    private final boolean parse;

    public TokenDefinition(@NotNull final Pattern pattern, @Nullable final PrefixAstBuilder<N, S> prefixBuilder, @Nullable final InfixAstBuilder<N, S> infixBuilder, @NotNull final String tokenTypeName, boolean parse) {
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
        return pattern.pattern();
    }

    @Nullable
    public PrefixAstBuilder<N, S> getPrefixBuilder() {
        return prefixBuilder;
    }

    @Nullable
    public InfixAstBuilder<N, S> getInfixBuilder() {
        return infixBuilder;
    }

    @NotNull
    public String getTokenTypeName() {
        return tokenTypeName;
    }
}
