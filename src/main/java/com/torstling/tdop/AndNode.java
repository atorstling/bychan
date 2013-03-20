package com.torstling.tdop;

public class AndNode implements BooleanExpressionNode {
    private final BooleanExpressionNode left;
    private final BooleanExpressionNode right;

    public AndNode(BooleanExpressionNode left, BooleanExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public boolean evaluate(VariableBindings bindings) {
        throw new UnsupportedOperationException();
    }
}
