package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
    public Map<String, VariableNode> getVariables() {
        throw new IllegalStateException();
    }
}
