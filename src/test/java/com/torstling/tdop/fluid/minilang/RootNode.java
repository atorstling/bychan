package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RootNode implements LaiLaiNode {
    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public Scope getScope() {
        return new RootScope();
    }
}
