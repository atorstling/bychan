package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableNode implements LaiLaiNode {
    @NotNull
    private final ExpressionType type;
    @NotNull
    private final String name;
    @Nullable
    private Object value;

    public VariableNode(@NotNull final ExpressionType type, @NotNull final String name) {
        this.type = type;
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

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return type;
    }

    @Override
    public String toString() {
        return type.name().toLowerCase() + "(" + name + ")";
    }

    public void setValue(@SuppressWarnings("NullableProblems") @NotNull final Object value) {
        this.value = value;
    }
}
