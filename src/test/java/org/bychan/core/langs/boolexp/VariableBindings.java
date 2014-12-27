package org.bychan.core.langs.boolexp;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VariableBindings {
    private final Map<String, Boolean> bindings;

    public VariableBindings(@NotNull final Map<String, Boolean> bindings) {
        this.bindings = new HashMap<>(bindings);
    }

    public boolean isSet(@NotNull final String variableName) {
        Boolean boundValue = bindings.get(variableName);
        if (boundValue == null) {
            throw new IllegalStateException("No value bound to variable '" + variableName + "'");
        }
        return boundValue;
    }
}
