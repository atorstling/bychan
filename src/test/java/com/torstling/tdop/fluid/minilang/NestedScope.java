package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NestedScope implements LaiLaiSymbolTable {

    @Nullable
    private LaiLaiNode child;
    @NotNull
    private final Map<String, VariableNode> variablesByName;
    @NotNull
    private final Variables variables;

    public NestedScope(@NotNull final LaiLaiSymbolTable parent) {
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
    @Override
    public Variables getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return "(s " + child + ")";
    }
}
