package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AssignNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode parent;
    @NotNull
    private final VariableNode left;
    @NotNull
    private final LaiLaiNode right;

    public AssignNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        if (!(left instanceof VariableNode)) {
            throw new IllegalArgumentException("Cannot assign to non-variable node '" + left + "'");
        }
        this.parent = parent;
        this.left = (VariableNode) left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        Object rhsValue = right.evaluate(currentScope);
        left.assign(rhsValue, currentScope);
        return rhsValue;
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return ExpressionType.union(left.getExpressionType(currentScope), right.getExpressionType(currentScope));
    }

    public String toString() {
        return "(= " + left + " " + right + ")";
    }
}
