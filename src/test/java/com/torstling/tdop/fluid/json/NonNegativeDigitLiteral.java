package com.torstling.tdop.fluid.json;

/**
 * Created by alext on 2/26/14.
 */
public class NonNegativeDigitLiteral {
    private final short value;

    public NonNegativeDigitLiteral(short value) {
        this.value = value;
    }

    public NonNegativeDigitLiteral(int value) {
        this((short) value);
    }

    public short getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NonNegativeDigitLiteral that = (NonNegativeDigitLiteral) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) value;
    }
}
