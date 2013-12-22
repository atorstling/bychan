package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class AssignNode implements LaiLaiNode {
    private final VariableNode left;
    private final LaiLaiNode right;

    public AssignNode(VariableNode left, LaiLaiNode right) {
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
    public ExpressionType getExpressionType() {
        return ExpressionType.union(left.getExpressionType(), right.getExpressionType());
    }

    public String toString() {
        return "(= " + left + " " + right + ")";
    }
}
