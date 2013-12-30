package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScopeNode implements LaiLaiNode {

    @Nullable
    private LaiLaiNode child;
    @NotNull
    private final Map<String, VariableDefNode> variablesByName;
    @NotNull
    private final Variables variables;

    public ScopeNode(@NotNull final LaiLaiNode parent) {
        this.child = null;
        variablesByName = new HashMap<>();
        variables = new Variables() {
            @Nullable
            @Override
            public VariableDefNode find(@NotNull String name) {
                if (variablesByName.containsKey(name)) {
                    return variablesByName.get(name);
                }
                return parent.getVariables().find(name);
            }

            @Override
            public void put(@NotNull String name, @NotNull VariableDefNode node) {
                VariableDefNode oldValue = variablesByName.put(name, node);
                if (oldValue != null) {
                    throw new IllegalStateException("Duplicate definition of variable '" + name + "'");
                }
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
    public Object evaluate(@Nullable ScopeNode currentScope) {
        return getChild().evaluate(this);
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return getChild().getExpressionType(currentScope);
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
