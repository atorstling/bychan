package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class AssignNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode parent;
    @NotNull
    private final VariableNode left;
    @NotNull
    private final LaiLaiNode right;

    public AssignNode(@NotNull final LaiLaiNode parent, @NotNull final VariableNode left, @NotNull final LaiLaiNode right) {
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        Object rhsValue = right.evaluate();
        left.setValue(rhsValue);
        return rhsValue;
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

    public String toString() {
        return "(= " + left + " " + right + ")";
    }
}
