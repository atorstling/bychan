package com.torstling.tdop.fluid.minilang;

/**
 * Created by alext on 12/21/13.
 */
public class FloatNode implements LaiLaiNode {
    private final float value;

    public FloatNode(float value) {
        this.value = value;
    }

    @Override
    public Object evaluate() {
        return null;
    }
}
