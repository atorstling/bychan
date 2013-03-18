package com.torstling.tdop;


import com.sun.istack.internal.NotNull;

public class AdditionToken implements Token {
    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        CalculatorNode right = parser.expression(infixBindingPower());
        return new AdditionNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    public String toString() {
        return "+";
    }
}
