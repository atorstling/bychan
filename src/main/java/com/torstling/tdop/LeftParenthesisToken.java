package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class LeftParenthesisToken implements Token {

    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        CalculatorNode expression = parser.expression(0);
        parser.swallow(RightParenthesisToken.class);
        return expression;
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "(";
    }
}
