package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class XorNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;
    @NotNull
    private final LaiLaiNode parent;

    public XorNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.parent = parent;
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
    public Map<String, VariableNode> getVariables() {
        return parent.getVariables();
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
