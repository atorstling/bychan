package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class OrToken implements Token<BooleanExpressionNode> {
    public OrToken() {
    }

    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public BooleanExpressionNode infixParse(BooleanExpressionNode left, TokenParserCallback<BooleanExpressionNode> parser) {
        return new OrNode(left, parser.expression(infixBindingPower()));
    }

    public int infixBindingPower() {
        return 20;
    }
}
