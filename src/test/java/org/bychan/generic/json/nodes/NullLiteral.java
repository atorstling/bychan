package org.bychan.generic.json.nodes;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
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
