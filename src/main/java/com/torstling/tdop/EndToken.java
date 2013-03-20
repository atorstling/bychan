package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class EndToken implements Token {
    public Node prefixParse(@NotNull TokenParserCallback parser) {
        throw new IllegalStateException("Cannot parse end as expression");
    }

    public Node infixParse(Node left, TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 0;
    }

    public String toString() {
        return ".";
    }
}
