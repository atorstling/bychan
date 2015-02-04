package org.bychan.core.dynamic;

import org.bychan.core.RegexMatcher;
import org.bychan.core.StringMatcher;
import org.bychan.core.TokenMatcher;
import org.jetbrains.annotations.NotNull;

public class TokenDefinitionBuilder<N> {
    private TokenMatcher matcher;
    private DynamicNudParseAction<N> nud;
    private DynamicLedParseAction<N> led;
    private boolean keepAfterLexing;
    private String tokenName;
    private int leftBindingPower = 1;
    private final TokenDefinitionOwner<N> tokenDefinitionOwner;
    private TokenKey tokenKey;

    public TokenDefinitionBuilder<N> matchesString(String text) {
        this.matcher = new StringMatcher(text);
        return this;
    }

    public TokenDefinitionBuilder<N> matchesPattern(String pattern) {
        this.matcher = new RegexMatcher(pattern);
        return this;
    }

    public TokenDefinitionBuilder(@NotNull TokenDefinitionOwner<N> tokenDefinitionOwner) {
        this.tokenDefinitionOwner = tokenDefinitionOwner;
        keepAfterLexing = true;
    }

    public TokenDefinitionBuilder<N> nud(DynamicNudParseAction<N> nud) {
        this.nud = nud;
        return this;
    }

    @NotNull
    public TokenDefinition<N> build() {
        if (matcher == null) {
            throw new IllegalStateException("No matching pattern has been set");
        }
        if (tokenName == null) {
            tokenName = "token" + tokenDefinitionOwner.increaseUnnamedTokenCounter();
        }
        TokenDefinition<N> token = new TokenDefinition<>(matcher, nud, led, tokenName, keepAfterLexing, leftBindingPower);
        tokenDefinitionOwner.tokenBuilt(token);
        return token;
    }

    public TokenDefinitionBuilder<N> led(DynamicLedParseAction<N> led) {
        this.led = led;
        return this;
    }

    public TokenDefinitionBuilder<N> discardAfterLexing() {
        this.keepAfterLexing = false;
        return this;
    }

    public TokenDefinitionBuilder<N> named(String name) {
        this.tokenName = name;
        tokenKey = new TokenKey(tokenName);
        return this;
    }

    @NotNull
    public TokenDefinitionBuilder<N> matchesWhitespace() {
        return matchesPattern("\\s+");
    }

    public TokenDefinitionBuilder<N> leftBindingPower(int leftBindingPower) {
        this.leftBindingPower = leftBindingPower;
        return this;
    }

    @NotNull
    public TokenKey getKey() {
        if (tokenKey == null) {
            throw new IllegalStateException("You must set a name first");
        }
        return tokenKey;
    }

}
