package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScopeNode implements LaiLaiNode {

    @Nullable
    private LaiLaiNode child;

    public ScopeNode() {
        this.child = null;
    }

    @NotNull
    private LaiLaiNode getChild() {
        if (child == null) {
            throw new IllegalStateException("Child not set");
        }
        return child;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return getChild().evaluate();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        return getChild().getExpressionType();
    }

    @Override
    public String toString() {
        return "(s " + child + ")";
    }

    public void setChild(@SuppressWarnings("NullableProblems") @NotNull final LaiLaiNode child) {
        this.child = child;
    }
}
