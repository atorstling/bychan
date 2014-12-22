package com.torstling.bychan.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdditionNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final LaiLaiNode right;

    public AdditionNode(@NotNull final LaiLaiNode previous, @NotNull final LaiLaiNode right) {
        this.previous = previous;
        this.right = right;
    }

    private void checkTypes(ScopeNode currentScope) {
        if (!ExpressionType.INT.equals(previous.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("previous not of type int: " + previous);
        }
        if (!ExpressionType.INT.equals(right.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Right not of type int: " + right);
        }
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        checkTypes(currentScope);
        return ((Integer) previous.evaluate(currentScope)) + ((Integer) right.evaluate(currentScope));
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        ExpressionType previousType = previous.getExpressionType(currentScope);
        ExpressionType rightType = right.getExpressionType(currentScope);
        return ExpressionType.union(previousType, rightType);
    }

    @Nullable
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        return "(+ " + previous + " " + right + ")";
    }
}
