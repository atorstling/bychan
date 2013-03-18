package com.torstling.tdop;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */
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
