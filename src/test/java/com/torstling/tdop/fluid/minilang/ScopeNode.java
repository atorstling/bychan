package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScopeNode implements LaiLaiNode {
    @NotNull
    private final Scope scope;
    @Nullable
    private LaiLaiNode child;

    public ScopeNode(@NotNull final Scope scope) {
        this.scope = scope;
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
    public Object evaluate(@Nullable ScopeNode currentScope) {
        return getChild().evaluate(this);
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return getChild().getExpressionType(currentScope);
    }

    @Override
    public String toString() {
        return "(s " + child + ")";
    }

    public void setChild(@SuppressWarnings("NullableProblems") @NotNull final LaiLaiNode child) {
        this.child = child;
    }

    @NotNull
    public Scope getScope() {
        return scope;
    }
}
