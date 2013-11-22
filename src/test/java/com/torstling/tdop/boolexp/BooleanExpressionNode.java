package com.torstling.tdop.boolexp;

import com.torstling.tdop.AstNode;
import com.torstling.tdop.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode extends AstNode {
    boolean evaluate(@NotNull VariableBindings bindings);
}
