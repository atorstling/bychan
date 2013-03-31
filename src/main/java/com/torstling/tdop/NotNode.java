package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class NotNode implements BooleanExpressionNode {
    private BooleanExpressionNode expression;

    public NotNode(BooleanExpressionNode expression) {
        this.expression = expression;
    }

    public boolean evaluate(@NotNull VariableBindings bindings) {
        return !expression.evaluate(bindings);
    }
}
