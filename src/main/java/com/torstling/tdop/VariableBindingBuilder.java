package com.torstling.tdop;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VariableBindingBuilder {
    @NotNull
    private final Map<String, Boolean> bindings;

    public VariableBindingBuilder() {
        this.bindings = new HashMap<>();
    }

    @NotNull
    public VariableBindingBuilder bind(@NotNull final String name, final boolean value) {
        bindings.put(name, value);
        return this;
    }

    @NotNull
    public VariableBindings build() {
        return new VariableBindings(bindings);
    }
}
