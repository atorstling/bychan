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
    private final DynamicNudParseAction<N> nud;
    @Nullable
    private final DynamicLedParseAction<N> led;
    @NotNull
    private final String tokenName;
    private final boolean parse;
    private final int leftBindingPower;

    public TokenDefinition(@NotNull final Pattern pattern, @Nullable final DynamicNudParseAction<N> nud, @Nullable final DynamicLedParseAction<N> led, @NotNull final String tokenName, boolean parse, int leftBindingPower) {
        this.pattern = pattern;
        this.nud = nud;
        this.led = led;
        this.tokenName = tokenName;
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
        return tokenName;
    }

    @Nullable
    public DynamicNudParseAction<N> getNud() {
        return nud;
    }

    @Nullable
    public DynamicLedParseAction<N> getLed() {
        return led;
    }

    @NotNull
    public String tokenName() {
        return tokenName;
    }

    public int getLeftBindingPower() {
        return leftBindingPower;
    }

}
