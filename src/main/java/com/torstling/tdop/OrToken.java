package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class OrToken implements Token {
    public OrToken() {
    }

    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        return new OrNode(left, parser.expression(infixBindingPower()));
    }

    public int infixBindingPower() {
        return 20;
    }
}
