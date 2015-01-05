package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N> {
    private String pattern;
    private DynamicNudParseAction<N> nud;
    private DynamicLedParseAction<N> led;
    private boolean parsed;
    private String tokenName;
    private int leftBindingPower = 1;
    private final TokenDefinitionOwner<N> tokenDefinitionOwner;
    private TokenKey tokenKey;

    public TokenDefinitionBuilder<N> matchesString(String text) {
        return matchesPattern(Pattern.quote(text));
    }

    public TokenDefinitionBuilder<N> matchesPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public TokenDefinitionBuilder(@NotNull TokenDefinitionOwner<N> tokenDefinitionOwner) {
        this.tokenDefinitionOwner = tokenDefinitionOwner;
        parsed = true;
    }

    public TokenDefinitionBuilder<N> nud(DynamicNudParseAction<N> nud) {
        this.nud = nud;
        return this;
    }

    @NotNull
    public TokenDefinition<N> build() {
        if (pattern == null) {
            throw new IllegalStateException("No matching pattern has been set");
        }
        if (tokenName == null) {
            tokenName = "token" + tokenDefinitionOwner.increaseUnnamedTokenCounter();
        }
        TokenDefinition<N> token = new TokenDefinition<>(Pattern.compile(pattern), nud, led, tokenName, parsed, leftBindingPower);
        tokenDefinitionOwner.tokenBuilt(token);
        return token;
    }

    public TokenDefinitionBuilder<N> led(DynamicLedParseAction<N> led) {
        this.led = led;
        return this;
    }

    public TokenDefinitionBuilder<N> ignoredWhenParsing() {
        this.parsed = false;
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
    public TokenDefinitionBuilder<N> ignoreWhenParsing() {
        return ignoredWhenParsing();
    }

    public TokenKey getKey() {
        return tokenKey;
    }

    @NotNull
    public TokenDefinition<N> end() {
        return build();
    }
}
