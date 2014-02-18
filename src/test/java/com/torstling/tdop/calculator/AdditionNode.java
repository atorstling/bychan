package com.torstling.tdop.calculator;

public class AdditionNode implements CalculatorNode {
    private final CalculatorNode previous;
    private final CalculatorNode right;

    public AdditionNode(CalculatorNode previous, CalculatorNode right) {
        this.previous = previous;
        this.right = right;
    }

    public int evaluate() {
        return previous.evaluate() + right.evaluate();
    }
}
