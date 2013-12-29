package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BooleanLiteralNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode parent;
    private final boolean value;

    public BooleanLiteralNode(@NotNull final LaiLaiNode parent, final boolean value) {
        this.parent = parent;
        this.value = value;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return value;
    }

    @NotNull
    @Override
    public Map<String, VariableNode> getVariables() {
        return parent.getVariables();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.BOOL;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
