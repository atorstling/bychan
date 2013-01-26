package com.torstling.tdop;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdditionNode implements Node {
    private final Node left;
    private final Node right;

    public AdditionNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public int evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
