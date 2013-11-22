package com.torstling.tdop.boolexp;

import com.torstling.tdop.Node;
import com.torstling.tdop.VariableBindings;
import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode extends Node {
    boolean evaluate(@NotNull VariableBindings bindings);
}
