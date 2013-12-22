package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class BooleanLiteralNode implements LaiLaiNode {
    private final boolean value;

    public BooleanLiteralNode(boolean value) {
        this.value = value;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return value;
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
