package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * The recorded definition of a dynamic token. Utilized to form both {@link DynamicToken}s and
 * {@link DynamicLexeme}s
 *
 *
 */
public class TokenDefinition<N> {
    @NotNull
    private final Pattern pattern;
    @Nullable
    private final DynamicPrefixParseAction<N> prefixBuilder;
    @Nullable
    private final DynamicInfixParseAction<N> infixBuilder;
    @NotNull
    private final String tokenName;
    private final boolean parse;
    private final int leftBindingPower;
    private final TokenKey tokenKey;

    public TokenDefinition(@NotNull final Pattern pattern, @Nullable final DynamicPrefixParseAction<N> prefixBuilder, @Nullable final DynamicInfixParseAction<N> infixBuilder, @NotNull final String tokenName, boolean parse, int leftBindingPower) {
        this.pattern = pattern;
        this.prefixBuilder = prefixBuilder;
        this.infixBuilder = infixBuilder;
        this.tokenName = tokenName;
        this.parse = parse;
        this.leftBindingPower = leftBindingPower;
        this.tokenKey = new TokenKey(tokenName());
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
        return tokenName;
    }

    @Nullable
    public DynamicPrefixParseAction<N> getPrefixBuilder() {
        return prefixBuilder;
    }

    @Nullable
    public DynamicInfixParseAction<N> getInfixBuilder() {
        return infixBuilder;
    }

    @NotNull
    public String tokenName() {
        return tokenName;
    }

    public int getLeftBindingPower() {
        return leftBindingPower;
    }

    public TokenKey getKey() {
        return tokenKey;
    }
}
