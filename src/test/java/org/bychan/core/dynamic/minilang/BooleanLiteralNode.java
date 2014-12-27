package org.bychan.core.dynamic.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanLiteralNode implements LaiLaiNode {
    private final boolean value;

    public BooleanLiteralNode(final boolean value) {
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
        return ExpressionType.BOOL;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return null;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
