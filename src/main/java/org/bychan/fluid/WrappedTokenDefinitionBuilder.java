package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;

/**
 * Wrapper which auto-adds token definition to language builder when done.
 *
 * @param <N>
 */
public class WrappedTokenDefinitionBuilder<N> {
    @NotNull
    private final TokenDefinitionBuilder<N> delegate;
    @NotNull
    private final LanguageBuilder<N> languageBuilder;
    private int leftBindingPower;

    public WrappedTokenDefinitionBuilder(@NotNull LanguageBuilder<N> languageBuilder, @NotNull final TokenDefinitionBuilder<N> delegate) {
        this.languageBuilder = languageBuilder;
        this.delegate = delegate;
    }

    public WrappedTokenDefinitionBuilder<N> matchesString(String text) {
        delegate.matchesString(text);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> named(String name) {
        delegate.named(name);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> prefixParseAs(PrefixAstBuilder<N> prefixBuilder) {
        delegate.prefixParseAs(prefixBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> infixParseAs(InfixAstBuilder<N> infixBuilder) {
        delegate.infixParseAs(infixBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> standaloneParseAs(StandaloneAstBuilder<N> nStandaloneAstBuilder) {
        delegate.standaloneParseAs(nStandaloneAstBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> ignoreWhenParsing() {
        delegate.ignoredWhenParsing();
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> matchesPattern(String pattern) {
        delegate.matchesPattern(pattern);
        return this;
    }

    @NotNull
    public LanguageBuilder<N> completeToken() {
        buildAndAdd();
        return languageBuilder;
    }

    @NotNull
    public TokenDefinition<N> completeTokenAndPause() {
        return buildAndAdd();
    }

    @NotNull
    private TokenDefinition<N> buildAndAdd() {
        TokenDefinition<N> tokenDefinition = delegate.build();
        languageBuilder.addToken(tokenDefinition);
        return tokenDefinition;
    }

    @NotNull
    public LanguageBuilder<N> newToken() {
        return completeToken();
    }

    @NotNull
    public TokenDefinitionBuilder<N> newLevelToken() {
        return completeToken().newLevel().startToken();
    }

    @NotNull
    public Language<N> completeLanguage() {
        return completeToken().completeLanguage();
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> bindingPower(int leftBindingPower) {
        this.leftBindingPower = leftBindingPower;
        return this;
    }
}
