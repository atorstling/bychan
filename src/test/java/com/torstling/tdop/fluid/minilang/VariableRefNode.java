package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableRefNode implements VariableNode {
    @NotNull
    private final LaiLaiNode parent;
    @NotNull
    private final String name;

    public VariableRefNode(@NotNull final LaiLaiNode parent, @NotNull final String name) {
        this.parent = parent;
        this.name = name;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        VariableDefNode variable = getVariable(currentScope);
        return variable.evaluate(currentScope);
    }

    @NotNull
    private VariableDefNode getVariable(@Nullable ScopeNode currentScope) {
        if (currentScope == null) {
            throw new IllegalStateException();
        }
        VariableDefNode variable = currentScope.getVariables().find(name);
        if (variable == null) {
            throw new IllegalStateException();
        }
        return variable;
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        VariableDefNode variable = getVariable(currentScope);
        return variable.getExpressionType(currentScope);
    }

    @NotNull
    @Override
    public Variables getVariables() {
        return parent.getVariables();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void assign(@NotNull Object value, @NotNull ScopeNode currentScope) {
        Variables variables = currentScope.getVariables();
        VariableDefNode variable = variables.find(name);
        if (variable == null) {
            throw new IllegalStateException("Reference to variable '" + name + " which is not yet defined or out of scope");
        }
        variable.setValue(value);
    }
}
