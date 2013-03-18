package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class MultiplicationToken implements Token {

    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        CalculatorNode right = parser.expression(infixBindingPower());
        return new MultiplicationNode(left, right);
    }

    public int infixBindingPower() {
        return 20;
    }

    public String toString() {
        return "*";
    }
}
