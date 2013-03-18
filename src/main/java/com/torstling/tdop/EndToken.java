package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class EndToken implements Token {
    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        throw new IllegalStateException("Cannot parse end as expression");
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 0;
    }

    public String toString() {
        return ".";
    }
}
