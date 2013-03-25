package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 3/22/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TokenDefinitionBuilder<N extends Node> {
    private String pattern;
    private PrefixAstBuilder<N> prefixBuilder;
    private InfixAstBuilder<BooleanExpressionNode> infixBuilder;

    public TokenDefinitionBuilder(String pattern) {
        this.pattern = pattern;
    }

    public TokenDefinitionBuilder<N> supportsPrefix(PrefixAstBuilder<N> prefixBuilder) {
        this.prefixBuilder = prefixBuilder;
        return this;
    }

    @NotNull
    public TokenDefinition<N> build() {
        return new TokenDefinition<N>(prefixBuilder, infixBuilder);
    }

    public TokenDefinitionBuilder<N> supportsInfix(InfixAstBuilder<BooleanExpressionNode> infixBuilder) {
        this.infixBuilder = infixBuilder;
        return this;
    }
}
