package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class OrToken implements Token<BooleanExpressionNode> {
    public OrToken() {
    }

    @NotNull
    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public BooleanExpressionNode infixParse(@NotNull BooleanExpressionNode left, @NotNull TokenParserCallback<BooleanExpressionNode> parser) {
        return new OrNode(left, parser.expression(infixBindingPower()));
    }

    public int infixBindingPower() {
        return 20;
    }

    @Override
    public boolean isOfType(@NotNull TokenType<BooleanExpressionNode> type) {
        return type.equals(OrTokenType.get());
    }
}
