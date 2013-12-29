package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class ScopeNode implements LaiLaiNode {

    @NotNull
    private final LaiLaiNode child;

    public ScopeNode(@NotNull final LaiLaiNode child) {
        this.child = child;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return child.evaluate();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return child.getExpressionType();
    }

    @Override
    public String toString() {
        return "(s " + child + ")";
    }
}
