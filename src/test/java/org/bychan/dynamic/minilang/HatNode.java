package org.bychan.dynamic.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HatNode implements LaiLaiNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final LaiLaiNode right;

    public HatNode(@NotNull final LaiLaiNode previous, @NotNull final LaiLaiNode right) {
        this.previous = previous;
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
        return previous.getExpressionType(currentScope);
    }

    @NotNull
    private LaiLaiNode chooseActualImplementation(@NotNull ExpressionType actualExpressionType) {
        if (ExpressionType.BOOL.equals(actualExpressionType)) {
            return new XorNode(previous, right);
        } else if (ExpressionType.FLOAT.equals(actualExpressionType)) {
            return new PowNode(previous, right);
        }
        throw new IllegalStateException("'hat' only applicable to bool and float, got '" + previous + "' of type '" + actualExpressionType + "'");
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return getSubExpressionType(currentScope);
    }

    @Nullable
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        return "(^ " + previous + " " + right + ")";
    }
}
