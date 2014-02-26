package com.torstling.tdop.fluid.json;

/**
 * Created by alext on 2/26/14.
 */
public class NullLiteral {
    public static final NullLiteral INSTANCE = new NullLiteral();

    private NullLiteral() {
    }

    public static NullLiteral get() {
        return INSTANCE;
    }
}
