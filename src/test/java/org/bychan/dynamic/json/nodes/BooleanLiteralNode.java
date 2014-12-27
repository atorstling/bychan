package org.bychan.dynamic.json.nodes;

import org.jetbrains.annotations.NotNull;

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

        return value == that.value;

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
