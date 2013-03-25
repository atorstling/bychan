package com.torstling.tdop;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 3/22/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TokenDefinition<N extends Node> {
    private PrefixAstBuilder<N> prefixBuilder;

    public TokenDefinition(PrefixAstBuilder<N> prefixBuilder, InfixAstBuilder<BooleanExpressionNode> infixBuilder) {
        this.prefixBuilder = prefixBuilder;
    }
}
