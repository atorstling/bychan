package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class VariableNode implements BooleanExpressionNode {
    private String name;

    public VariableNode(@NotNull final String name) {
        this.name = name;
    }

    public boolean evaluate(VariableBindings bindings) {
        return bindings.isSet(name);
    }
}
