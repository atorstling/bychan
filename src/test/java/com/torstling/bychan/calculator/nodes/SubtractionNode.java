package com.torstling.bychan.calculator.nodes;

public class SubtractionNode implements CalculatorNode {
    private final CalculatorNode previous;
    private final CalculatorNode right;

    public SubtractionNode(CalculatorNode previous, CalculatorNode right) {
        this.previous = previous;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubtractionNode that = (SubtractionNode) o;

        if (previous != null ? !previous.equals(that.previous) : that.previous != null) return false;
        //noinspection RedundantIfStatement
        if (right != null ? !right.equals(that.right) : that.right != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = previous != null ? previous.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    public int evaluate() {
        return previous.evaluate() - right.evaluate();
    }

    public String toString() {
        return "(" + previous.toString() + "-" + right.toString() + ")";
    }
}
