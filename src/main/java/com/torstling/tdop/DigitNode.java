package com.torstling.tdop;

public class DigitNode implements Node {
    private final int value;

    public DigitNode(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DigitNode digitNode = (DigitNode) o;

        if (value != digitNode.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public int evaluate() {
        return value;
    }
}
