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
    private final FluidLevelLanguageBuilder<N> languageBuilder;

    public WrappedTokenDefinitionBuilder(@NotNull FluidLevelLanguageBuilder<N> languageBuilder, @NotNull final TokenDefinitionBuilder<N> delegate) {
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
    public FluidLevelLanguageBuilder<N> completeToken() {
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
    public WrappedTokenDefinitionBuilder<N> newToken() {
        return completeToken().startToken();
    }

    @NotNull
    public WrappedTokenDefinitionBuilder<N> newLevelToken() {
        return completeToken().newLevel().startToken();
    }

    @NotNull
    public Language<N> completeLanguage() {
        return completeToken().completeLanguage();
    }
}
