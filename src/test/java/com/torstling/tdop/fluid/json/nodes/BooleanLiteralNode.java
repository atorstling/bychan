package com.torstling.tdop.fluid.json.nodes;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2014-12-05.
 */
public class BooleanLiteralNode implements JsonNode {
    private final boolean value;

    public BooleanLiteralNode(boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanLiteralNode that = (BooleanLiteralNode) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }

    @NotNull
    @Override
    public String prettyPrint(int depth) {
        return Boolean.toString(value);
    }
}
