package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatLiteralNode implements LiteralNode {
    @NotNull
    private final LaiLaiNode parent;
    private final float value;

    public FloatLiteralNode(@NotNull final LaiLaiNode parent, float value) {
        this.parent = parent;
        this.value = value;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        return value;
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
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
