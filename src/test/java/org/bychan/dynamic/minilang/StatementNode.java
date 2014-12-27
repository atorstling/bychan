package org.bychan.dynamic.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatementNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final LaiLaiNode right;

    public StatementNode(@NotNull final LaiLaiNode previous, @NotNull final LaiLaiNode right) {
        this.previous = previous;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        previous.evaluate(currentScope);
        return right.evaluate(currentScope);
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.union(previous.getExpressionType(currentScope), right.getExpressionType(currentScope));
    }

    @Nullable
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        return "(x " + previous + " " + right + ")";
    }
}
