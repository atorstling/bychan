package org.bychan.core.langs.calculator.nodes;

public class NegationNode implements CalculatorNode {
    private final CalculatorNode child;

    public NegationNode(CalculatorNode child) {
        this.child = child;
    }

    @Override
    public Integer evaluate() {
        return -1 * child.evaluate();
    }
}
