package com.torstling.tdop.fluid.minilang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 12/21/13.
 */
public class FloatLiteralNode implements LiteralNode {
    private final float value;

    public FloatLiteralNode(float value) {
        this.value = value;
    }

    @NotNull
    @Override
    public Object evaluate() {
        return value;
    }
}
