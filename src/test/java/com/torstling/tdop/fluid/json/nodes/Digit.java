package com.torstling.tdop.fluid.json.nodes;

/**
 * Created by alext on 2/26/14.
 */
public class Digit {
    private final short value;

    public Digit(short value) {
        this.value = value;
    }

    public Digit(int value) {
        this((short) value);
    }

    public short getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Digit that = (Digit) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) value;
    }
}
