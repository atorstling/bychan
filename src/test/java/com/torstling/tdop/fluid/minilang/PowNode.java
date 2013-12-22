package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;


public class PowNode implements LaiLaiNode {
    private final LaiLaiNode left;
    private final LaiLaiNode right;

    public PowNode(LaiLaiNode left, LaiLaiNode right) {
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
    public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }

    @Override
    public String toString() {
        return "(pow " + left + " " + right + ")";
    }
}
