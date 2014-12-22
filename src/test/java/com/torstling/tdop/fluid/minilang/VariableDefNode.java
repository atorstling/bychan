package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableDefNode implements VariableNode {
    @NotNull
    private final LaiLaiNode previous;
    @NotNull
    private final ExpressionType type;
    @NotNull
    private final String name;
    @Nullable
    private Object value;

    public VariableDefNode(@NotNull LaiLaiNode previous, @NotNull final ExpressionType type, @NotNull final String name) {
        this.previous = previous;
        this.type = type;
        this.name = name;
        this.value = null;
    }

    @NotNull
    @Override
    public Object evaluate(@Nullable ScopeNode currentScope) {
        if (value == null) {
            throw new RuntimeException("Value of variable '" + name + "' has not been set.");
        }
        return value;
    }

    @NotNull
    @Override
    public ExpressionType getExpressionType(@Nullable ScopeNode currentScope) {
        return type;
    }

    @Nullable
    @Override
    public Scope getScope() {
        return previous.getScope();
    }

    @Override
    public String toString() {
        return type.name().toLowerCase() + "(" + name + ")";
    }

    public void setValue(@SuppressWarnings("NullableProblems") @NotNull final Object value) {
        this.value = value;
    }


    @Override
    public void assign(@NotNull Object value, @NotNull ScopeNode currentScope) {
        Scope scope = currentScope.getScope();
        this.value = value;
        scope.put(name, this);
    }
}
