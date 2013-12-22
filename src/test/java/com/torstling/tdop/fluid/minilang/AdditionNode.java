package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class AdditionNode implements LaiLaiNode {
    private final LaiLaiNode left;
    private final LaiLaiNode right;

    public AdditionNode(LaiLaiNode left, LaiLaiNode right) {
        if (!ExpressionType.INT.equals(left.getExpressionType())) {
            throw new IllegalArgumentException("Left not of type int: " + left);
        }
        if (!ExpressionType.INT.equals(right.getExpressionType())) {
            throw new IllegalArgumentException("Right not of type int: " + right);
        }
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return ((Integer) left.evaluate()) + ((Integer) right.evaluate());
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        ExpressionType leftType = left.getExpressionType();
        ExpressionType rightType = right.getExpressionType();
        return ExpressionType.union(leftType, rightType);
    }

    @Override
    public String toString() {
        return "(+ " + left + " " + right + ")";
    }
}
