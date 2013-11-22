package com.torstling.tdop.boolexp;

import com.torstling.tdop.VariableBindings;
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
