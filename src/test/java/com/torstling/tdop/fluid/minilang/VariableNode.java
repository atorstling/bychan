package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class VariableNode implements LaiLaiNode {
    @NotNull
    private final String name;
    private Object value;

    public VariableNode(@NotNull final String name) {
        this.name = name;
        this.value = null;
    }

    @NotNull
    @Override
    public Object evaluate() {
        if (value == null) {
            throw new RuntimeException("Value of variable '" + name + "' has not been set.");
        }
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setValue(@NotNull final Object value) {
        this.value = value;
    }
}
