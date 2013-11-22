package com.torstling.tdop.calculator;

public class MultiplicationNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public MultiplicationNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public int evaluate() {
        return left.evaluate() * right.evaluate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiplicationNode that = (MultiplicationNode) o;

        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        //noinspection RedundantIfStatement
        if (right != null ? !right.equals(that.right) : that.right != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
}
