package com.torstling.tdop.fluid.json.nodes;

/**
 * Created by alext on 2/26/14.
 */
public class NullLiteral implements JsonNode {
    public static final NullLiteral INSTANCE = new NullLiteral();

    private NullLiteral() {
    }

    public static NullLiteral get() {
        return INSTANCE;
    }

    @Override
    public Object evaluate() {
        return null;
    }
}
