package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiniLangRootNode implements LaiLaiNode {
    @NotNull
    @Override
    public Object evaluate() {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType() {
        throw new IllegalStateException();
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return new Variables() {
            @Nullable
            @Override
            public VariableNode find(@NotNull String name) {
                return null;
            }

            @Override
            public void put(@NotNull String name, @NotNull VariableNode node) {
                throw new IllegalStateException();
            }
        };
    }
}
