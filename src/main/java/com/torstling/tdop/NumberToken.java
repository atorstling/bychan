package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class NumberToken implements Token<CalculatorNode> {
    private int value;

    public NumberToken(int value) {
        this.value = value;
    }

    public static Token valueOf(String digit) {
        return new NumberToken(Integer.parseInt(digit));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberToken that = (NumberToken) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }


    @NotNull
    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        return new NumberNode(value);
    }

    @NotNull
    public CalculatorNode infixParse(@NotNull CalculatorNode left, @NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
