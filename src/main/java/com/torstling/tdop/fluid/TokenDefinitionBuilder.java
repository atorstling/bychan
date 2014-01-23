package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N extends AstNode, S> {
    private String pattern;
    private PrefixAstBuilder<N, S> prefixBuilder;
    private InfixAstBuilder<N, S> infixBuilder;
    private StandaloneAstBuilder<N, S> standaloneBuilder;
    private boolean ignoredWhenParsing;
    private String tokenTypeName;

    public TokenDefinitionBuilder<N, S> matchesString(String text) {
        return matchesPattern(Pattern.quote(text));
    }

    public TokenDefinitionBuilder<N, S> matchesPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public TokenDefinitionBuilder() {
        ignoredWhenParsing = false;
    }

    public TokenDefinitionBuilder<N, S> supportsPrefix(PrefixAstBuilder<N, S> prefixBuilder) {
        this.prefixBuilder = prefixBuilder;
        return this;
    }

    @NotNull
    public TokenDefinition<N, S> build() {
        if (pattern == null) {
            throw new IllegalStateException("No matching pattern has been set");
        }
        return new TokenDefinition<N, S>(Pattern.compile(pattern), selectPrefix(), infixBuilder, tokenTypeName, ignoredWhenParsing);
    }

    @Nullable
    private PrefixAstBuilder<N, S> selectPrefix() {
        if (standaloneBuilder != null && prefixBuilder != null) {
            throw new IllegalStateException("Prefix and standalone matchers cannot be simultaneously defined.");
        }
        if (standaloneBuilder != null) {
            return new PrefixAstBuilder<N, S>() {
                @NotNull
                @Override
                public N build(@NotNull S parent, @NotNull LexingMatch match, @NotNull ParserCallback2<N, S> parser) {
                    return standaloneBuilder.build(parent, match);
                }
            };
        } else {
            return prefixBuilder;
        }
    }

    public TokenDefinitionBuilder<N, S> supportsInfix(InfixAstBuilder<N, S> infixBuilder) {
        this.infixBuilder = infixBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N, S> ignoredWhenParsing() {
        this.ignoredWhenParsing = true;
        return this;
    }

    public TokenDefinitionBuilder<N, S> supportsStandalone(StandaloneAstBuilder<N, S> standaloneAstBuilder) {
        this.standaloneBuilder = standaloneAstBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N, S> named(String name) {
        this.tokenTypeName = name;
        return this;
    }
}
