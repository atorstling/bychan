package com.torstling.tdop.calculator.nodes;

public class NegationNode implements CalculatorNode {
    private final CalculatorNode child;

    public NegationNode(CalculatorNode child) {
        this.child = child;
    }

    @Override
    public int evaluate() {
        return -1 * child.evaluate();
    }
}
