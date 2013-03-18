package com.torstling.tdop;

public class OrNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public OrNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public int evaluate() {
        throw new UnsupportedOperationException();
    }
}
