package com.torstling.tdop;

public class NumberNode implements Node {
    private final int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberNode numberNode = (NumberNode) o;

        return value == numberNode.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public int evaluate() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
