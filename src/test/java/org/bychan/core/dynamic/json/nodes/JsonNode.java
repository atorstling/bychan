package org.bychan.core.dynamic.json.nodes;

import org.jetbrains.annotations.NotNull;

public interface JsonNode {
    @NotNull
    String prettyPrint(int depth);
}
