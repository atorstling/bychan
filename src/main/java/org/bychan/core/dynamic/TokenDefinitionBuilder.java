package org.bychan.core.dynamic;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N> {
    private final TokenDefinitionOwner<N> tokenDefinitionOwner;
    private TokenMatcher matcher;
    private DynamicNudParseAction<N> nud;
    private DynamicLedParseAction<N> led;
    private boolean keepAfterLexing;
    private String tokenName;
    private int leftBindingPower = 1;
    private TokenKey tokenKey;
    private String documentation;

    public TokenDefinitionBuilder(@NotNull TokenDefinitionOwner<N> tokenDefinitionOwner) {
        this.tokenDefinitionOwner = tokenDefinitionOwner;
        keepAfterLexing = true;
    }

    @NotNull
    public TokenDefinitionBuilder<N> matchesString(@NotNull final String text) {
        return matches(new StringMatcher(text));
    }

    @NotNull
    public TokenDefinitionBuilder<N> matches(@NotNull final TokenMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    @NotNull
    public TokenDefinitionBuilder<N> matchesPattern(@NotNull final String pattern) {
        return matchesPattern(Pattern.compile(pattern));
    }

    @NotNull
    public TokenDefinitionBuilder<N> matchesPattern(@NotNull final Pattern pattern) {
        return matches(new RegexMatcher(pattern));
    }

    @NotNull
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
        TokenDefinition<N> token = new TokenDefinition<>(matcher, nud, led, tokenName, documentation, keepAfterLexing, leftBindingPower);
        tokenDefinitionOwner.tokenBuilt(token);
        return token;
    }

    @NotNull
    public TokenDefinitionBuilder<N> led(@NotNull final DynamicLedParseAction<N> led) {
        this.led = led;
        return this;
    }

    @NotNull
    public TokenDefinitionBuilder<N> discardAfterLexing() {
        this.keepAfterLexing = false;
        return this;
    }

    @NotNull
    public TokenDefinitionBuilder<N> named(@NotNull final String name) {
        this.tokenName = name;
        tokenKey = TokenKey.byName(tokenName);
        return this;
    }

    @NotNull
    public TokenDefinitionBuilder<N> matchesWhitespace() {
        return matchesPattern("\\s+");
    }

    @NotNull
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

    @NotNull
    public TokenDefinitionBuilder<N> doc(@NotNull final String documentation) {
        this.documentation = documentation;
        return this;
    }

    @NotNull
    public TokenDefinitionBuilder<N> matchesAnyPermutationOf(List<String> strings, Collection<String> ignoredSeparators) {
        return matches(new AnyPermutationMatcher(strings, ignoredSeparators));
    }
}
