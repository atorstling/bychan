package com.torstling.tdop.boolexp;

import com.torstling.tdop.VariableBindings;
import org.jetbrains.annotations.NotNull;

public class AndNode implements BooleanExpressionNode {
    private final BooleanExpressionNode left;
    private final BooleanExpressionNode right;

    public AndNode(BooleanExpressionNode left, BooleanExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public boolean evaluate(@NotNull VariableBindings bindings) {
        return left.evaluate(bindings) && right.evaluate(bindings);
    }
}
