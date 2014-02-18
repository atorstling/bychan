package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatLiteralNode implements LiteralNode {
    @NotNull
    private final LaiLaiNode previous;
    private final float value;

    public FloatLiteralNode(@NotNull LaiLaiNode previous, float value) {
        this.previous = previous;
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

    @NotNull
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        return value + "f";
    }
}
