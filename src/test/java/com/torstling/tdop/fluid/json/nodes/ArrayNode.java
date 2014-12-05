package com.torstling.tdop.fluid.json.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArrayNode implements JsonNode {
    @NotNull
    private final List<? extends JsonNode> subNodes;

    public ArrayNode(@NotNull final List<? extends JsonNode> subNodes) {
        this.subNodes = new ArrayList<>(subNodes);
    }

    @Override
    public Object evaluate() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayNode arrayNode = (ArrayNode) o;

        if (!subNodes.equals(arrayNode.subNodes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return subNodes.hashCode();
    }
}
