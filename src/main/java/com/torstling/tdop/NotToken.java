package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class NotToken implements Token<BooleanExpressionNode> {

    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new NotNode(parser.expression(infixBindingPower()));
    }

    public BooleanExpressionNode infixParse(BooleanExpressionNode left, TokenParserCallback<BooleanExpressionNode> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 1;
    }
}
