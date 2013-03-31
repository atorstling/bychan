package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode extends Node {
    boolean evaluate(@NotNull VariableBindings bindings);
}
