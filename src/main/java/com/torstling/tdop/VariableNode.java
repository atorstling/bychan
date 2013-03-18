package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class VariableNode implements CalculatorNode {
    private String name;

    public VariableNode(@NotNull final String name) {
        this.name = name;
    }

    public int evaluate() {
        throw new UnsupportedOperationException();
    }
}
