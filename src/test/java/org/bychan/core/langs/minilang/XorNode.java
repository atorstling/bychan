package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XorNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;

    public XorNode(@NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    private void checkTypes(@NotNull ScopeNode currentScope) {
        if (!ExpressionType.BOOL.equals(left.getExpressionType(currentScope))) {
            throw new IllegalArgumentException("left subExpression needs to be of type bool: " + left);
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
        return ((Boolean) left.evaluate(currentScope)) ^ ((Boolean) right.evaluate(currentScope));
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
        return "(xor " + left + " " + right + ")";
    }
}
