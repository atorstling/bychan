package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerLiteralNode implements LiteralNode {
    @NotNull
    private final LaiLaiNode parent;
    private final int value;

    public IntegerLiteralNode(@NotNull final LaiLaiNode parent, final int value) {
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
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.INT;
    }

    @Override
    public String toString() {
        return value + "i";
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
    }
}
