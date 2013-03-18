package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VariableBindings {
    private Map<String, Boolean> bindings;

    public VariableBindings(@NotNull final Map<String, Boolean> bindings) {
        this.bindings = new HashMap<String, Boolean>(bindings);
    }

    public boolean isSet(@NotNull final String variableName) {
        return bindings.get(variableName);
    }
}
