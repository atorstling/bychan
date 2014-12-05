package com.torstling.tdop.fluid.json.nodes;

/**
 * Created by alext on 2014-12-05.
 */
public class NumberLiteralNode implements JsonNode {
    private final float f;

    public NumberLiteralNode(float f) {
        this.f = f;
    }

    @Override
    public Object evaluate() {
        return f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberLiteralNode that = (NumberLiteralNode) o;

        if (Float.compare(that.f, f) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (f != +0.0f ? Float.floatToIntBits(f) : 0);
    }

    @Override
    public String toString() {
        return "NumberLiteralNode{" +
                "f=" + f +
                '}';
    }
}
