package com.torstling.bychan.boolexp;

import com.torstling.bychan.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public class NotNode implements BooleanExpressionNode {
    private final BooleanExpressionNode expression;

    public NotNode(BooleanExpressionNode expression) {
        this.expression = expression;
    }

    public boolean evaluate(@NotNull VariableBindings bindings) {
        return !expression.evaluate(bindings);
    }
}
