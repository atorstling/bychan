package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DigitToken implements Token {
    private int value;

    public DigitToken(int value) {
        this.value = value;
    }

    public static Token valueOf(String digit) {
        return new DigitToken(Integer.parseInt(digit));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DigitToken that = (DigitToken) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }


    public Node suffixParse(@NotNull TokenParserCallback parser) {
        return new DigitNode(value);
    }

    public Node infixParse(Node left, TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
