package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 12/22/13.
 */
public class StatementNode implements LaiLaiNode {
    private final LaiLaiNode left;
    private final LaiLaiNode right;

    public StatementNode(LaiLaiNode left, LaiLaiNode right) {
        this.left = left;
        this.right = right;
    }

    @NotNull
    @Override
    public Object evaluate() {
        left.evaluate();
        return right.evaluate();
    }

    @Override
    public String toString() {
        return "(x " + left + " " + right + ")";
    }
}
