package org.bychan.core.langs.boolexp;

import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode {
    boolean evaluate(@NotNull VariableBindings bindings);
}
