package com.torstling.tdop;

public class AdditionNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public AdditionNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public int evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
