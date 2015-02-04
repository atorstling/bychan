package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableDefNode implements VariableNode {
    @NotNull
    private final LaiLaiNode left;
    @NotNull
    private final ExpressionType type;
    @NotNull
    private final String name;
    @Nullable
    private Object value;

    public VariableDefNode(@NotNull LaiLaiNode left, @NotNull final ExpressionType type, @NotNull final String name) {
        this.left = left;
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
        return left.getScope();
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
