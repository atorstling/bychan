package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class AndToken implements Token<BooleanExpressionNode> {
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException("'And' operator cannot be used as a prefix");
    }

    public BooleanExpressionNode infixParse(BooleanExpressionNode left, TokenParserCallback<BooleanExpressionNode> parser) {
        BooleanExpressionNode right = parser.expression(infixBindingPower());
        return new AndNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }
}

