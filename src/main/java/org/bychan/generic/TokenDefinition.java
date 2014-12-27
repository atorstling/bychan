package org.bychan.generic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * The recorded definition of a dynamic token. Utilized to form both {@link org.bychan.generic.GenericTokenType}s and
 * {@link org.bychan.generic.GenericToken}s
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
    private final int leftBindingPower;

    public TokenDefinition(@NotNull final Pattern pattern, @Nullable final PrefixAstBuilder<N> prefixBuilder, @Nullable final InfixAstBuilder<N> infixBuilder, @NotNull final String tokenTypeName, boolean parse, int leftBindingPower) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
        this.tokenTypeName = tokenTypeName;
        this.parse = parse;
        this.leftBindingPower = leftBindingPower;
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

    public int getLeftBindingPower() {
        return leftBindingPower;
    }
}
