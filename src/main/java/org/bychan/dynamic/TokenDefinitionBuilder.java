package org.bychan.dynamic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N> {
    private String pattern;
    private DynamicPrefixParseAction<N> prefixBuilder;
    private DynamicInfixParseAction<N> infixBuilder;
    private DynamicStandaloneParseAction<N> standaloneBuilder;
    private boolean parsed;
    private String tokenTypeName;
    private int leftBindingPower = 1;
    private LanguageBuilder<N> languageBuilder;

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

    public TokenDefinitionBuilder<N> prefixParseAs(DynamicPrefixParseAction<N> prefixBuilder) {
        this.prefixBuilder = prefixBuilder;
        return this;
    }

    @NotNull
    public TokenDefinition<N> build() {
        if (pattern == null) {
            throw new IllegalStateException("No matching pattern has been set");
        }
        return new TokenDefinition<>(Pattern.compile(pattern), selectPrefix(), infixBuilder, tokenTypeName, parsed, leftBindingPower);
    }

    @Nullable
    private DynamicPrefixParseAction<N> selectPrefix() {
        if (standaloneBuilder != null && prefixBuilder != null) {
            throw new IllegalStateException("Prefix and standalone matchers cannot be simultaneously defined.");
        }
        if (standaloneBuilder != null) {
            return (previous, match, parser) -> standaloneBuilder.parse(previous, match);
        } else {
            return prefixBuilder;
        }
    }

    public TokenDefinitionBuilder<N> infixParseAs(DynamicInfixParseAction<N> infixBuilder) {
        this.infixBuilder = infixBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N> ignoredWhenParsing() {
        this.parsed = false;
        return this;
    }

    public TokenDefinitionBuilder<N> standaloneParseAs(DynamicStandaloneParseAction<N> dynamicStandaloneParseAction) {
        this.standaloneBuilder = dynamicStandaloneParseAction;
        return this;
    }

    public TokenDefinitionBuilder<N> named(String name) {
        this.tokenTypeName = name;
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

    public Language<N> completeLanguage() {
        completeToken();
        return languageBuilder.completeLanguage();
    }

    @NotNull
    public TokenDefinition<N> completeTokenAndPause() {
        TokenDefinition<N> result = build();
        languageBuilder.addToken(result);
        return result;
    }

    @NotNull
    public LanguageBuilder<N> completeToken() {
        return languageBuilder.addToken(build());
    }

    public TokenDefinitionBuilder<N> newToken() {
        completeToken();
        return languageBuilder.newToken();
    }

    @NotNull
    public TokenDefinitionBuilder<N> ignoreWhenParsing() {
        return ignoredWhenParsing();
    }
}
