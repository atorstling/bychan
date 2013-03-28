package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class NotToken implements Token<BooleanExpressionNode> {

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new NotNode(parser.expression(infixBindingPower()));
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 1;
    }
}
