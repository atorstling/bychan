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
    public Node suffixParse(@NotNull TokenParserCallback parser) {
            throw new UnsupportedOperationException();
        }

        public Node infixParse(Node left, TokenParserCallback parser) {
            throw new UnsupportedOperationException();
        }

        public int infixBindingPower() {
            return 0;
        }
}
