package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NestedScope implements Scope {
    @NotNull
    private final Scope leftScope;
    @NotNull
    private final Map<String, VariableDefNode> variablesByName;

    public NestedScope(@NotNull final Scope leftScope) {
        this.leftScope = leftScope;
        variablesByName = new HashMap<>();
    }

    @Nullable
    @Override
    public VariableDefNode find(@NotNull String name) {
        if (variablesByName.containsKey(name)) {
            return variablesByName.get(name);
        }
        return leftScope.find(name);
    }

    @Override
    public void put(@NotNull String name, @NotNull VariableDefNode node) {
        variablesByName.put(name, node);
    }
}
