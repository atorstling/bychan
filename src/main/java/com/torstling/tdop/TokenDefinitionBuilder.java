package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.regex.Pattern;

public class TokenDefinitionBuilder<N extends Node> {
    private String pattern;
    private PrefixAstBuilder<N> prefixBuilder;
    private InfixAstBuilder<BooleanExpressionNode> infixBuilder;
    private boolean filterOutBeforeParsing;
    private StandaloneAstBuilder<BooleanExpressionNode> standaloneAstBuilder;

    public TokenDefinitionBuilder<N> matchesString(String text) {
        return matchesPattern(Pattern.quote(text));
    }

    public TokenDefinitionBuilder<N> matchesPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public TokenDefinitionBuilder() {
        filterOutBeforeParsing = false;
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
        return new TokenDefinition<N>(prefixBuilder, infixBuilder);
    }

    public TokenDefinitionBuilder<N> supportsInfix(InfixAstBuilder<BooleanExpressionNode> infixBuilder) {
        this.infixBuilder = infixBuilder;
        return this;
    }

    public TokenDefinitionBuilder<N> filterOutBeforeParsing() {
        this.filterOutBeforeParsing = true;
        return this;
    }

    public TokenDefinitionBuilder<N> supportsStandalone(StandaloneAstBuilder<BooleanExpressionNode> standaloneAstBuilder) {
        this.standaloneAstBuilder = standaloneAstBuilder;
        return this;
    }
}
