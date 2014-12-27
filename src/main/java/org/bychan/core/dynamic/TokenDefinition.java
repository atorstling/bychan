package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * The recorded definition of a dynamic token. Utilized to form both {@link DynamicTokenType}s and
 * {@link DynamicToken}s
 *
 * @param <N>
 */
public class TokenDefinition<N> {
    @NotNull
    private final Pattern pattern;
    @Nullable
    private final DynamicPrefixParseAction<N> prefixBuilder;
    @Nullable
    private final DynamicInfixParseAction<N> infixBuilder;
    @NotNull
    private final String tokenTypeName;
    private final boolean parse;
    private final int leftBindingPower;

    public TokenDefinition(@NotNull final Pattern pattern, @Nullable final DynamicPrefixParseAction<N> prefixBuilder, @Nullable final DynamicInfixParseAction<N> infixBuilder, @NotNull final String tokenTypeName, boolean parse, int leftBindingPower) {
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
    public DynamicPrefixParseAction<N> getPrefixBuilder() {
        return prefixBuilder;
    }

    @Nullable
    public DynamicInfixParseAction<N> getInfixBuilder() {
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
