package com.torstling.tdop.fluid.json.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectNode implements JsonNode {
    @NotNull
    private final Map<StringLiteralNode, JsonNode> pairs;

    public ObjectNode(@NotNull Map<StringLiteralNode, JsonNode> pairs) {
        this.pairs = new LinkedHashMap<>(pairs);
    }

    @Override
    public Object evaluate() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectNode that = (ObjectNode) o;

        if (!pairs.equals(that.pairs)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pairs.hashCode();
    }
}
