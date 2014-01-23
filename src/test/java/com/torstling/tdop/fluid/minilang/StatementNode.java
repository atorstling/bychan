package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class StatementNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;

    public StatementNode(@NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        left.evaluate();
        return right.evaluate();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.union(left.getExpressionType(), right.getExpressionType());
    }

    @Override
    public String toString() {
        return "(x " + left + " " + right + ")";
    }
}
