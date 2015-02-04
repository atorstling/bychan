package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatementNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;

    public StatementNode(@NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        left.evaluate(currentScope);
        return right.evaluate(currentScope);
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.union(left.getExpressionType(currentScope), right.getExpressionType(currentScope));
    }

    @Nullable
    @Override
    public Scope getScope() {
        return left.getScope();
    }

    @Override
    public String toString() {
        return "(x " + left + " " + right + ")";
    }
}
