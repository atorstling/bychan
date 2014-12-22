package com.torstling.bychan.boolexp;

import com.torstling.bychan.core.AstNode;
import com.torstling.bychan.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public interface BooleanExpressionNode extends AstNode {
    boolean evaluate(@NotNull VariableBindings bindings);
}
