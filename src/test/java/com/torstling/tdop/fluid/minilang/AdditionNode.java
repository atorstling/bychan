package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AdditionNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;
    @NotNull
    private final LaiLaiNode parent;

    public AdditionNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.parent = parent;
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

    @NotNull
    @Override
    public Map<String, VariableNode> getVariables() {
        return parent.getVariables();
    }

    @Override
    public String toString() {
        return "(+ " + left + " " + right + ")";
    }
}
