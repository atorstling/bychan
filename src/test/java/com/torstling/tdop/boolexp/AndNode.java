package com.torstling.tdop.boolexp;

import com.torstling.tdop.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public class AndNode implements BooleanExpressionNode {
    private final BooleanExpressionNode previous;
    private final BooleanExpressionNode right;

    public AndNode(BooleanExpressionNode previous, BooleanExpressionNode right) {
        this.previous = previous;
        this.right = right;
    }

    public boolean evaluate(@NotNull VariableBindings bindings) {
        return previous.evaluate(bindings) && right.evaluate(bindings);
    }
}
