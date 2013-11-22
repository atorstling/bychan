package com.torstling.tdop.boolexp;

import com.torstling.tdop.fluid.VariableBindings;
import org.jetbrains.annotations.NotNull;

public class OrNode implements BooleanExpressionNode {
    private final BooleanExpressionNode left;
    private final BooleanExpressionNode right;

    public OrNode(BooleanExpressionNode left, BooleanExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public boolean evaluate(@NotNull VariableBindings bindings) {
        boolean leftResult = left.evaluate(bindings);
        return leftResult || right.evaluate(bindings);
    }
}