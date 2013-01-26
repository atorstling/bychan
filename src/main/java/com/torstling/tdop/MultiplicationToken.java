package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class MultiplicationToken implements Token {

    public Node prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public Node infixParse(Node left, TokenParserCallback parser) {
        Node right = parser.expression(infixBindingPower());
        return new MultiplicationNode(left, right);
    }

    public int infixBindingPower() {
        return 20;
    }

    public String toString() {
        return "*";
    }
}
