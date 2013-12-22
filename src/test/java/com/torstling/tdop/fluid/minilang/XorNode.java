package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class XorNode implements LaiLaiNode {
    private final LaiLaiNode left;
    private final LaiLaiNode right;

    public XorNode(LaiLaiNode left, LaiLaiNode right) {
        if (!ExpressionType.BOOL.equals(left.getExpressionType())) {
            throw new IllegalArgumentException("Left expression needs to be of type bool: " + left);
        }
        if (!ExpressionType.BOOL.equals(right.getExpressionType())) {
            throw new IllegalArgumentException("Right expression needs to be of type bool: " + right);
        }
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return ((Boolean) left.evaluate()) ^ ((Boolean) right.evaluate());
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.BOOL;
    }

    @Override
    public String toString() {
        return "(xor " + left + " " + right + ")";
    }
}
