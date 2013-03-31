package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class AndToken implements Token<BooleanExpressionNode> {
    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException("'And' operator cannot be used as a prefix");
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        BooleanExpressionNode right = parser.expression(infixBindingPower());
        return new AndNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    @Override
    public boolean isOfType(@NotNull final TokenType<BooleanExpressionNode> type) {
        return type.equals(AndTokenType.get());
    }
}

