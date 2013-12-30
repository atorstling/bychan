package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return ExpressionType.BOOL;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
