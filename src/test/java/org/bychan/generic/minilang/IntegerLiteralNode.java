package org.bychan.generic.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerLiteralNode implements LiteralNode {
    @NotNull
    private final LaiLaiNode previous;
    private final int value;

    public IntegerLiteralNode(@NotNull LaiLaiNode previous, final int value) {
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
        return ExpressionType.INT;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        return value + "i";
    }

}
