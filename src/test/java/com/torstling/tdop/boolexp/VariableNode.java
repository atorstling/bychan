package com.torstling.tdop.boolexp;

import com.torstling.tdop.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public class VariableNode implements BooleanExpressionNode {
    private final String name;

    public VariableNode(@NotNull final String name) {
        this.name = name;
    }

    public boolean evaluate(@org.jetbrains.annotations.NotNull VariableBindings bindings) {
        return bindings.isSet(name);
    }
}
