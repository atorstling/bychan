package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XorNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;
    @NotNull
    private final LaiLaiNode parent;

    public XorNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.parent = parent;

        this.left = left;
        this.right = right;
    }

    private void checkTypes(@NotNull ScopeNode currentScope) {
        if (!ExpressionType.BOOL.equals(left.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Left expression needs to be of type bool: " + left);
        }
        if (!ExpressionType.BOOL.equals(right.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Right expression needs to be of type bool: " + right);
        }
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        checkTypes(currentScope);
        return ((Boolean) left.evaluate(currentScope)) ^ ((Boolean) right.evaluate(currentScope));
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.BOOL;
    }

    @Override
    public String toString() {
        return "(xor " + left + " " + right + ")";
    }
}
