package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubtractionToken implements Token {
    public Node prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public Node infixParse(Node left, TokenParserCallback parser) {
        Node right = parser.expression(infixBindingPower());
        return new SubtractionNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    public String toString() {
        return "-";
    }
}
