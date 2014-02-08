package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PowNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;

    public PowNode(@NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    private void checkTypes(ScopeNode currentScope) {
        if (!ExpressionType.FLOAT.equals(left.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Left must be of float type:" + left);
        }
        if (!ExpressionType.FLOAT.equals(right.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Right must be of float type:" + left);
        }
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        checkTypes(currentScope);
        return (float) Math.pow(((float) left.evaluate(currentScope)), ((float) right.evaluate(currentScope)));
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.FLOAT;
    }

    @Override
    public String toString() {
        return "(pow " + left + " " + right + ")";
    }
}
