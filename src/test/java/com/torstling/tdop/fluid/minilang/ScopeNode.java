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
    @NotNull
    private final Variables variables;

    public ScopeNode(@NotNull final LaiLaiNode parent) {
        this.child = null;
        variablesByName = new HashMap<>();
        variables = new Variables() {
            @Nullable
            @Override
            public VariableNode find(@NotNull String name) {
                if (variablesByName.containsKey(name)) {
                    return variablesByName.get(name);
                }
                return parent.getVariables().find(name);
            }

            @Override
            public void put(@NotNull String name, @NotNull VariableNode node) {
                variablesByName.put(name, node);
            }
        };
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
    public Variables getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return "(s " + child + ")";
    }

    public void setChild(@SuppressWarnings("NullableProblems") @NotNull final LaiLaiNode child) {
        this.child = child;
    }
}
