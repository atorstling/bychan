package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class VariableToken implements Token {
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

    public CalculatorNode prefixParse(@NotNull TokenParserCallback parser) {
        return new VariableNode(name);
    }

    public CalculatorNode infixParse(CalculatorNode left, TokenParserCallback parser) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int infixBindingPower() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

