package org.bychan.core.dynamic.json.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayNode implements JsonNode {
    @NotNull
    private final List<? extends JsonNode> subNodes;

    public ArrayNode(@NotNull final List<? extends JsonNode> subNodes) {
        this.subNodes = new ArrayList<>(subNodes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayNode arrayNode = (ArrayNode) o;

        return subNodes.equals(arrayNode.subNodes);

    }

    @Override
    public int hashCode() {
        return subNodes.hashCode();
    }

    @NotNull
    @Override
    public String prettyPrint(int depth) {
        List<String> prettyPrintedElements = subNodes.stream().map(new Function<JsonNode, String>() {
            @Override
            public String apply(JsonNode j) {
                return j.prettyPrint(depth+1);
            }
        }).collect(Collectors.toList());
        return "[" + String.join(", ", prettyPrintedElements) + "]";
    }
}
