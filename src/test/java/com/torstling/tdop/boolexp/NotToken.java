package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class NotToken<S> implements Token<BooleanExpressionNode, S> {

    @NotNull
    private final LexingMatch match;

    public NotToken(@NotNull LexingMatch match) {
        this.match = match;
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        return new NotNode(parser.tryParse(new ExpressionParserStrategy<>(previous, infixBindingPower())).getRootNode());
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode previous, @NotNull TokenParserCallback<BooleanExpressionNode, S> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 1;
    }

    @Override
    @NotNull
    public TokenType<BooleanExpressionNode, S> getType() {
        return NotTokenType.get();
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
