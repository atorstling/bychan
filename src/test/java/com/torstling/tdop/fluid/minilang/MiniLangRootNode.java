package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiniLangRootNode implements LaiLaiNode {
    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        return null;
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return null;
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return new Variables() {
            @Nullable
            @Override
            public VariableDefNode find(@NotNull String name) {
                return null;
            }

            @Override
            public void put(@NotNull String name, @NotNull VariableDefNode node) {
                throw new IllegalStateException();
            }
        };
    }
}
