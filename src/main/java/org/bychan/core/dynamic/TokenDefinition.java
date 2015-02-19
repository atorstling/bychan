package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The recorded definition of a dynamic token. Utilized to form both {@link DynamicToken}s and
 * {@link DynamicLexeme}s
 */
public class TokenDefinition<N> {
    @NotNull
    private final TokenMatcher matcher;
    @Nullable
    private final DynamicNudParseAction<N> nud;
    @Nullable
    private final DynamicLedParseAction<N> led;
    @NotNull
    private final String tokenName;
    @Nullable
    private final String documentation;
    private final boolean keepAfterLexing;
    private final int leftBindingPower;
    private final TokenKey tokenKey;

    public TokenDefinition(@NotNull final TokenMatcher matcher, @Nullable final DynamicNudParseAction<N> nud, @Nullable final DynamicLedParseAction<N> led, @NotNull final String tokenName, @Nullable final String documentation, boolean keepAfterLexing, int leftBindingPower) {
        this.matcher = matcher;
        this.nud = nud;
        this.led = led;
        this.tokenName = tokenName;
        this.documentation = documentation;
        this.keepAfterLexing = keepAfterLexing;
        this.leftBindingPower = leftBindingPower;
        this.tokenKey = TokenKey.byName(tokenName());
    }

    @NotNull
    public TokenMatcher getMatcher() {
        return matcher;
    }

    public boolean keepAfterLexing() {
        return keepAfterLexing;
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

    public TokenKey getKey() {
        return tokenKey;
    }

    @Nullable
    public String getDocumentation() {
        return documentation;
    }
}
