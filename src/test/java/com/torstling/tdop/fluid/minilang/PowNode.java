package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class PowNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;
    @NotNull
    private final LaiLaiNode parent;

    public PowNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.parent = parent;
        if (!ExpressionType.FLOAT.equals(left.getExpressionType())) {
            throw new IllegalArgumentException("Left must be of float type:" + left);
        }
        if (!ExpressionType.FLOAT.equals(right.getExpressionType())) {
            throw new IllegalArgumentException("Right must be of float type:" + left);
        }
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return (float) Math.pow(((float) left.evaluate()), ((float) right.evaluate()));
    }

    @NotNull
    @Override
    public Map<String, VariableNode> getVariables() {
        return parent.getVariables();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }

    @Override
    public String toString() {
        return "(pow " + left + " " + right + ")";
    }
}
