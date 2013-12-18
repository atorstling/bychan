package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

/**
 * Wrapper which auto-adds token definition to language builder when done.
 *
 * @param <N>
 */
public class WrappedTokenDefinitionBuilder<N extends AstNode> {
    @NotNull
    private final TokenDefinitionBuilder<N> delegate;
    @NotNull
    private final LanguageBuilder2<N> languageBuilder;

    public WrappedTokenDefinitionBuilder(LanguageBuilder2<N> languageBuilder, @NotNull final TokenDefinitionBuilder<N> delegate) {
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

    public WrappedTokenDefinitionBuilder<N> supportsPrefix(PrefixAstBuilder<N> prefixBuilder) {
        delegate.supportsPrefix(prefixBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> supportsInfix(InfixAstBuilder<N> infixBuilder) {
        delegate.supportsInfix(infixBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N> supportsStandalone(StandaloneAstBuilder<N> nStandaloneAstBuilder) {
        delegate.supportsStandalone(nStandaloneAstBuilder);
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
    public LanguageBuilder2<N> completeToken() {
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
}
