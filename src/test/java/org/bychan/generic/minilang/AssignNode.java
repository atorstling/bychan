package org.bychan.generic.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AssignNode implements LaiLaiNode {
    @NotNull
    private final VariableNode previous;
    @NotNull
    private final LaiLaiNode right;

    public AssignNode(@NotNull final LaiLaiNode previous, @NotNull final LaiLaiNode right) {
        if (!(previous instanceof VariableNode)) {
            throw new IllegalArgumentException("Cannot assign to non-variable node '" + previous + "'");
        }
        this.previous = (VariableNode) previous;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        Object rhsValue = right.evaluate(currentScope);
        assert currentScope != null;
        previous.assign(rhsValue, currentScope);
        return rhsValue;
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

    public String toString() {
        return "(= " + previous + " " + right + ")";
    }
}
