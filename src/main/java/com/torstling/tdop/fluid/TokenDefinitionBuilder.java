package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N extends AstNode> {
    private String pattern;
    private PrefixAstBuilder<N> prefixBuilder;
    private InfixAstBuilder<N> infixBuilder;
    private StandaloneAstBuilder<N> standaloneBuilder;
    private boolean ignoredWhenParsing;
    private String tokenTypeName;

    public TokenDefinitionBuilder<N> matchesString(String text) {
        return matchesPattern(Pattern.quote(text));
    }

    public TokenDefinitionBuilder<N> matchesPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public TokenDefinitionBuilder() {
        ignoredWhenParsing = false;
    }

    public TokenDefinitionBuilder<N> supportsPrefix(PrefixAstBuilder<N> prefixBuilder) {
        this.prefixBuilder = prefixBuilder;
        return this;
    }

    @NotNull
    public TokenDefinition<N> build() {
        if (pattern == null) {
            throw new IllegalStateException("No matching pattern has been set");
        }
        return new TokenDefinition<>(pattern, selectPrefix(), infixBuilder, tokenTypeName, ignoredWhenParsing);
    }

    @Nullable
    private PrefixAstBuilder<N> selectPrefix() {
        if (standaloneBuilder != null && prefixBuilder != null) {
            throw new IllegalStateException("Prefix and standalone matchers cannot be simultaneously defined.");
        }
        if (standaloneBuilder != null) {
            return new PrefixAstBuilder<N>() {
                @NotNull
                @Override
                public N build(@NotNull LexingMatch match, @NotNull ParserCallback2<N> parser) {
                    return standaloneBuilder.build(match);
                }
            };
        } else {
            return prefixBuilder;
        }
    }

    public TokenDefinitionBuilder<N> supportsInfix(InfixAstBuilder<N> infixBuilder) {
        this.infixBuilder = infixBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N> ignoredWhenParsing() {
        this.ignoredWhenParsing = true;
        return this;
    }

    public TokenDefinitionBuilder<N> supportsStandalone(StandaloneAstBuilder<N> standaloneAstBuilder) {
        this.standaloneBuilder = standaloneAstBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N> named(String name) {
        this.tokenTypeName = name;
        return this;
    }
}
