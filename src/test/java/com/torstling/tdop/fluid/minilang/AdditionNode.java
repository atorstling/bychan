package com.torstling.tdop.fluid.minilang;

public class AdditionNode implements LaiLaiNode {
    private final LaiLaiNode left;
    private final LaiLaiNode right;

    public AdditionNode(LaiLaiNode left, LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object evaluate() {
        return null;
    }
}
