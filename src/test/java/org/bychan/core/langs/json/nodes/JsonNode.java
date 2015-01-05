package org.bychan.core.langs.json.nodes;

import org.jetbrains.annotations.NotNull;

public interface JsonNode {
    @NotNull
    String prettyPrint(int depth);
}
