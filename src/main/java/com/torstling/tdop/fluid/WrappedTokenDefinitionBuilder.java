package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

/**
 * Wrapper which auto-adds token definition to language builder when done.
 *
 * @param <N>
 */
public class WrappedTokenDefinitionBuilder<N extends AstNode, S> {
    @NotNull
    private final TokenDefinitionBuilder<N, S> delegate;
    @NotNull
    private final LanguageBuilder2<N, S> languageBuilder;

    public WrappedTokenDefinitionBuilder(LanguageBuilder2<N, S> languageBuilder, @NotNull final TokenDefinitionBuilder<N, S> delegate) {
        this.languageBuilder = languageBuilder;
        this.delegate = delegate;
    }

    public WrappedTokenDefinitionBuilder<N, S> matchesString(String text) {
        delegate.matchesString(text);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N, S> named(String name) {
        delegate.named(name);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N, S> supportsPrefix(PrefixAstBuilder<N, S> prefixBuilder) {
        delegate.supportsPrefix(prefixBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N, S> supportsInfix(InfixAstBuilder<N, S> infixBuilder) {
        delegate.supportsInfix(infixBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N, S> supportsStandalone(StandaloneAstBuilder<N, S> nStandaloneAstBuilder) {
        delegate.supportsStandalone(nStandaloneAstBuilder);
        return this;
    }

    public WrappedTokenDefinitionBuilder<N, S> ignoreWhenParsing() {
        delegate.ignoredWhenParsing();
        return this;
    }

    public WrappedTokenDefinitionBuilder<N, S> matchesPattern(String pattern) {
        delegate.matchesPattern(pattern);
        return this;
    }

    @NotNull
    public LanguageBuilder2<N, S> completeToken() {
        buildAndAdd();
        return languageBuilder;
    }

    @NotNull
    public TokenDefinition<N, S> completeTokenAndPause() {
        return buildAndAdd();
    }

    @NotNull
    private TokenDefinition<N, S> buildAndAdd() {
        TokenDefinition<N, S> tokenDefinition = delegate.build();
        languageBuilder.addToken(tokenDefinition);
        return tokenDefinition;
    }
}
