package com.torstling.bychan.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alext on 2/18/14.
 */
public class RootScope implements Scope {
    @NotNull
    private final Map<String, VariableDefNode> variablesByName;

    public RootScope() {
        variablesByName = new HashMap<>();
    }

    @Nullable
    @Override
    public VariableDefNode find(@NotNull String name) {
        return variablesByName.get(name);
    }

    @Override
    public void put(@NotNull String name, @NotNull VariableDefNode node) {
        variablesByName.put(name, node);
    }
}
