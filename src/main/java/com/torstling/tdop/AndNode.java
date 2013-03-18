package com.torstling.tdop;

public class AndNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public AndNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public int evaluate() {
        throw new UnsupportedOperationException();
    }
}
