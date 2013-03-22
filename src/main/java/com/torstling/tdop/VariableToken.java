package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class VariableToken implements Token<BooleanExpressionNode> {
    @NotNull
    private final String name;

    public VariableToken(@NotNull final String name) {
        if (!name.matches("[a-z]+")) {
            throw new IllegalArgumentException("Variable name can only contain lower-case letters, was '" + name + "'");
        }
        this.name = name;
    }

    @NotNull
    public static VariableToken valueOf(@NotNull final String value) {
        return new VariableToken(value);
    }

    public String getName() {
        return name;
    }

    public BooleanExpressionNode prefixParse(@NotNull TokenParserCallback parser) {
        return new VariableNode(name);
    }

    public BooleanExpressionNode infixParse(BooleanExpressionNode left, TokenParserCallback parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        throw new UnsupportedOperationException();
    }
}
