package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class StatementNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;
    @NotNull
    private final LaiLaiNode parent;

    public StatementNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @NotNull
    @Override
    public Object evaluate() {
        left.evaluate();
        return right.evaluate();
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
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
