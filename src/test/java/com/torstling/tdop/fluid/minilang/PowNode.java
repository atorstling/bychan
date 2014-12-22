package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PowNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final LaiLaiNode right;

    public PowNode(@NotNull final LaiLaiNode previous, @NotNull final LaiLaiNode right) {
        this.previous = previous;
        this.right = right;
    }

    private void checkTypes(ScopeNode currentScope) {
        if (!ExpressionType.FLOAT.equals(previous.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("previous must be of float type:" + previous);
        }
        if (!ExpressionType.FLOAT.equals(right.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Right must be of float type:" + previous);
        }
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        checkTypes(currentScope);
        return (float) Math.pow(((float) previous.evaluate(currentScope)), ((float) right.evaluate(currentScope)));
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.FLOAT;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return null;
    }

    @Override
    public String toString() {
        return "(pow " + previous + " " + right + ")";
    }
}
