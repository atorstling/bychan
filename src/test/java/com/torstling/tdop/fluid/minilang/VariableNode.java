package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class VariableNode implements LaiLaiNode {
    @NotNull
    private final String name;

    public VariableNode(@NotNull final String name) {
        this.name = name;
    }

    @Override
    public Object evaluate() {
        return 1;
    }
}
