package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XorNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final LaiLaiNode right;

    public XorNode(@NotNull final LaiLaiNode previous, @NotNull final LaiLaiNode right) {
        this.previous = previous;
        this.right = right;
    }

    private void checkTypes(@NotNull ScopeNode currentScope) {
        if (!ExpressionType.BOOL.equals(previous.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("previous subExpression needs to be of type bool: " + previous);
        }
        if (!ExpressionType.BOOL.equals(right.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("Right subExpression needs to be of type bool: " + right);
        }
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        assert currentScope != null;
        checkTypes(currentScope);
        return ((Boolean) previous.evaluate(currentScope)) ^ ((Boolean) right.evaluate(currentScope));
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.BOOL;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return null;
    }

    @Override
    public String toString() {
        return "(xor " + previous + " " + right + ")";
    }
}
