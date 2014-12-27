package org.bychan.generic.json.nodes;

import org.bychan.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectNode implements JsonNode {
    @NotNull
    private final Map<StringLiteralNode, JsonNode> pairs;

    public ObjectNode(@NotNull Map<StringLiteralNode, JsonNode> pairs) {
        this.pairs = new LinkedHashMap<>(pairs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectNode that = (ObjectNode) o;

        return pairs.equals(that.pairs);

    }

    @Override
    public int hashCode() {
        return pairs.hashCode();
    }

    @NotNull
    @Override
    public String prettyPrint(int depth) {
        String indentation = StringUtils.repeat("  ", depth);
        ArrayList<String> pairStrings = new ArrayList<>();
        for (Map.Entry<StringLiteralNode, JsonNode> entry : pairs.entrySet()) {
            pairStrings.add(indentation + entry.getKey().prettyPrint(depth+1) + ": " + entry.getValue().prettyPrint(depth+1));
        }
        return "{\n" + String.join(",\n", pairStrings) + "\n"+ indentation +"}";
    }
}
