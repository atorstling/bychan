package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScopeNode implements LaiLaiNode {

    @Nullable
    private LaiLaiNode child;
    @NotNull
    private final Map<String, VariableNode> variablesByName;

    public ScopeNode() {
        this.child = null;
        variablesByName = new HashMap<>();
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

    @NotNull
    @Override
    public Map<String, VariableNode> getVariables() {
        return variablesByName;
    }

    @Override
    public String toString() {
        return "(s " + child + ")";
    }

    public void setChild(@SuppressWarnings("NullableProblems") @NotNull final LaiLaiNode child) {
        this.child = child;
    }
}
