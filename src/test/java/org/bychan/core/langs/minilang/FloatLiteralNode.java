package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatLiteralNode implements LiteralNode {
    @NotNull
    private final LaiLaiNode left;
    private final float value;

    public FloatLiteralNode(@NotNull LaiLaiNode left, float value) {
        this.left = left;
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

    @Nullable
    @Override
    public Scope getScope() {
        return left.getScope();
    }

    @Override
    public String toString() {
        return value + "f";
    }
}
