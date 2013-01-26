package com.torstling.tdop;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 1/26/13
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubtractionNode implements Node {
    private final Node left;
    private final Node right;

    public SubtractionNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubtractionNode that = (SubtractionNode) o;

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

    public int evaluate() {
        return left.evaluate() - right.evaluate();
    }
}
