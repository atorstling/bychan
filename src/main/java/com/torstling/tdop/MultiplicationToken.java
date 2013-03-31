package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class MultiplicationToken implements Token<CalculatorNode> {

    @NotNull
    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public CalculatorNode infixParse(@NotNull CalculatorNode left, @NotNull TokenParserCallback<CalculatorNode> parser) {
        CalculatorNode right = parser.expression(infixBindingPower());
        return new MultiplicationNode(left, right);
    }

    public int infixBindingPower() {
        return 20;
    }

    public String toString() {
        return "*";
    }

    @Override
    public boolean isOfType(@NotNull TokenType<CalculatorNode> type) {
        return type.equals(MultiplicationTokenType.get());
    }
}
