package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdditionNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;

    public AdditionNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    private void checkTypes(ScopeNode currentScope) {
        if (!ExpressionType.INT.equals(left.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Left not of type int: " + left);
        }
        if (!ExpressionType.INT.equals(right.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Right not of type int: " + right);
        }
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        checkTypes(currentScope);
        return ((Integer) left.evaluate(currentScope)) + ((Integer) right.evaluate(currentScope));
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        ExpressionType leftType = left.getExpressionType(currentScope);
        ExpressionType rightType = right.getExpressionType(currentScope);
        return ExpressionType.union(leftType, rightType);
    }

    @Override
    public String toString() {
        return "(+ " + left + " " + right + ")";
    }
}
