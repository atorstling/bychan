package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class AndToken<S> implements Token<BooleanExpressionNode, S> {
    private final LexingMatch match;

    public AndToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull S parent, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        throw new UnsupportedOperationException("'And' operator cannot be used as a prefix");
    }

    @NotNull
    public BooleanExpressionNode infixParse(S parent, @NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        BooleanExpressionNode right = parser.tryParse(new ExpressionParserStrategy<>(parent, infixBindingPower())).getRootNode();
        return new AndNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    @Override
    @NotNull
    public TokenType<BooleanExpressionNode, S> getType() {
        return AndTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}

