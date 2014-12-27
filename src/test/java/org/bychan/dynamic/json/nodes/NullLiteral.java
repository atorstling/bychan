package org.bychan.dynamic.json.nodes;

import org.jetbrains.annotations.NotNull;

public class NullLiteral implements JsonNode {
    private static final NullLiteral INSTANCE = new NullLiteral();

    private NullLiteral() {
    }

    public static NullLiteral get() {
        return INSTANCE;
    }

    @NotNull
    @Override
    public String prettyPrint(int depth) {
        return "null";
    }
}
