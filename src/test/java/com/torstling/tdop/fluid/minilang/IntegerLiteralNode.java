package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class IntegerLiteralNode implements LiteralNode {
    private final int value;

    public IntegerLiteralNode(final int value) {
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
        return ExpressionType.INT;
    }

    @Override
    public String toString() {
        return value + "i";
    }

}
