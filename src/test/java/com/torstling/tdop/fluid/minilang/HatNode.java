package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HatNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode parent;
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final LaiLaiNode right;

    public HatNode(@NotNull final LaiLaiNode parent, @NotNull final LaiLaiNode left, @NotNull final LaiLaiNode right) {
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        ExpressionType actualExpressionType = getSubExpressionType(currentScope);
        LaiLaiNode actualImplementation = chooseActualImplementation(actualExpressionType);
        return actualImplementation.evaluate(currentScope);
    }

    @NotNull
    private ExpressionType getSubExpressionType(@Nullable ScopeNode currentScope) {
        return left.getExpressionType(currentScope);
    }

    @NotNull
    private LaiLaiNode chooseActualImplementation(@NotNull ExpressionType actualExpressionType) {
        if (ExpressionType.BOOL.equals(actualExpressionType)) {
            return new XorNode(parent, left, right);
        } else if (ExpressionType.FLOAT.equals(actualExpressionType)) {
            return new PowNode(parent, left, right);
        }
        throw new IllegalStateException("'hat' only applicable to bool and float, got '" + left + "' of type '" + actualExpressionType + "'");
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return getSubExpressionType(currentScope);
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
    }

    @Override
    public String toString() {
        return "(^ " + left + " " + right + ")";
    }
}
