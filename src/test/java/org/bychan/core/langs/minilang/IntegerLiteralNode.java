package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerLiteralNode implements LiteralNode {
    @NotNull
    private final LaiLaiNode left;
    private final int value;

    public IntegerLiteralNode(@NotNull LaiLaiNode left, final int value) {
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
        return ExpressionType.INT;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return left.getScope();
    }

    @Override
    public String toString() {
        return value + "i";
    }

}
