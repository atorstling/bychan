package com.torstling.tdop;

public class OrNode implements BooleanExpressionNode {
    private final BooleanExpressionNode left;
    private final BooleanExpressionNode right;

    public OrNode(BooleanExpressionNode left, BooleanExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public boolean evaluate(VariableBindings bindings) {
        throw new UnsupportedOperationException();
    }
}
