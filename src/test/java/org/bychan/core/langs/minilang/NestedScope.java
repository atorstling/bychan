package org.bychan.core.langs.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NestedScope implements Scope {
    @NotNull
    private final Scope previousScope;
    @NotNull
    private final Map<String, VariableDefNode> variablesByName;

    public NestedScope(@NotNull final Scope previousScope) {
        this.previousScope = previousScope;
        variablesByName = new HashMap<>();
    }

    @Nullable
    @Override
    public VariableDefNode find(@NotNull String name) {
        if (variablesByName.containsKey(name)) {
            return variablesByName.get(name);
        }
        return previousScope.find(name);
    }

    @Override
    public void put(@NotNull String name, @NotNull VariableDefNode node) {
        variablesByName.put(name, node);
    }
}
