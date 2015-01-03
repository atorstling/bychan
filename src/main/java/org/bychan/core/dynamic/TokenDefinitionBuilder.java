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
    private final LanguageBuilder<N> languageBuilder;
    private TokenKey tokenKey;

    public TokenDefinitionBuilder<N> matchesString(String text) {
        return matchesPattern(Pattern.quote(text));
    }

    public TokenDefinitionBuilder<N> matchesPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public TokenDefinitionBuilder(@NotNull LanguageBuilder<N> languageBuilder) {
        this.languageBuilder = languageBuilder;
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
            tokenName = "token" + languageBuilder.increaseUnnamedTokenCounter();
        }
        return new TokenDefinition<>(Pattern.compile(pattern), nud, led, tokenName, parsed, leftBindingPower);
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
    public TokenDefinition<N> buildAndAdd() {
        TokenDefinition<N> result = build();
        languageBuilder.addToken(result);
        return result;
    }

    @NotNull
    public LanguageBuilder<N> end() {
        return languageBuilder.addToken(build());
    }

    @NotNull
    public TokenDefinitionBuilder<N> ignoreWhenParsing() {
        return ignoredWhenParsing();
    }

    public TokenKey getKey() {
        return tokenKey;
    }
}
