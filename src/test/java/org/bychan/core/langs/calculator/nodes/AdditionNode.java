package org.bychan.core.langs.calculator.nodes;

public class AdditionNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public AdditionNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public Integer evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
