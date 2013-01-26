package com.torstling.tdop;

public class MultiplicationNode implements Node {
    private final Node left;
    private final Node right;

    public MultiplicationNode(Node left, Node right) {
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
