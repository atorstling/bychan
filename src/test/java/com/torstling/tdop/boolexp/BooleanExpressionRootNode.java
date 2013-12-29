package com.torstling.tdop.boolexp;

import com.torstling.tdop.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public class BooleanExpressionRootNode implements BooleanExpressionNode {
    @Override
    public boolean evaluate(@NotNull VariableBindings bindings) {
        throw new IllegalStateException();
    }
}
