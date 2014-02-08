package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatLiteralNode implements LiteralNode {
    private final float value;

    public FloatLiteralNode(float value) {
        this.value = value;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        return value;
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.FLOAT;
    }

    @Override
    public String toString() {
        return value + "f";
    }
}
