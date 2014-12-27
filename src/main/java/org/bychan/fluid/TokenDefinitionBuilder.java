package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N> {
    private String pattern;
    private PrefixAstBuilder<N> prefixBuilder;
    private InfixAstBuilder<N> infixBuilder;
    private StandaloneAstBuilder<N> standaloneBuilder;
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

    public TokenDefinitionBuilder<N> prefixParseAs(PrefixAstBuilder<N> prefixBuilder) {
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
    private PrefixAstBuilder<N> selectPrefix() {
        if (standaloneBuilder != null && prefixBuilder != null) {
            throw new IllegalStateException("Prefix and standalone matchers cannot be simultaneously defined.");
        }
        if (standaloneBuilder != null) {
            return (previous, match, parser) -> standaloneBuilder.build(previous, match);
        } else {
            return prefixBuilder;
        }
    }

    public TokenDefinitionBuilder<N> infixParseAs(InfixAstBuilder<N> infixBuilder) {
        this.infixBuilder = infixBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N> ignoredWhenParsing() {
        this.parsed = false;
        return this;
    }

    public TokenDefinitionBuilder<N> standaloneParseAs(StandaloneAstBuilder<N> standaloneAstBuilder) {
        this.standaloneBuilder = standaloneAstBuilder;
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

    public TokenDefinitionBuilder<N> newLevelToken() {
        return newToken();
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
