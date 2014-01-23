package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class FloatLiteralNode implements LiteralNode {
    private final float value;

    public FloatLiteralNode(float value) {
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
        return ExpressionType.FLOAT;
    }

    @Override
    public String toString() {
        return value + "f";
    }
}
