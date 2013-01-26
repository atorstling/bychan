package com.torstling.tdop;


import com.sun.istack.internal.NotNull;

public class AdditionToken implements Token {
    public Node prefixParse(@NotNull TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public Node infixParse(Node left, TokenParserCallback parser) {
        Node right = parser.expression(infixBindingPower());
        return new AdditionNode(left, right);
    }

    public int infixBindingPower() {
        return 10;
    }

    public String toString() {
        return "+";
    }
}
