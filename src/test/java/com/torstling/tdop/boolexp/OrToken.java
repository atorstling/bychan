package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class OrToken<S> implements Token<BooleanExpressionNode, S> {
    @NotNull
    private final LexingMatch match;

    public OrToken(@NotNull final LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        return new OrNode(previous, parser.tryParse(new ExpressionParserStrategy<>(previous, infixBindingPower())).getRootNode());
    }

    public int infixBindingPower() {
        return 20;
    }


    @Override
    @NotNull
    public TokenType<BooleanExpressionNode, S> getType() {
        return OrTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
