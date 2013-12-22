package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

public class AdditionNode implements LaiLaiNode {
    private final LaiLaiNode left;
    private final LaiLaiNode right;

    public AdditionNode(LaiLaiNode left, LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return ((Integer) left.evaluate()) + ((Integer) right.evaluate());
    }

    @Override
    public String toString() {
        return "(+ " + left + " " + right + ")";
    }
}
