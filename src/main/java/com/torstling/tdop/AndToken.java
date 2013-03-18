package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class AndToken implements Token {
    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException("'And' operator cannot be used as a prefix");
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        CalculatorNode right = parser.expression(infixBindingPower());
        return new AndNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }
}

