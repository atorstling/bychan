package org.bychan.core.utils;

public class TextPosition {
    private final int row;
    private final int col;

    public TextPosition(int row, int col) {
        if (row < 1) {
            throw new IllegalArgumentException("Row must be >0, was " + row);
        }
        if (row < 1) {
            throw new IllegalArgumentException("Col must be >0, was " + col);
        }
        this.row = row;
        this.col = col;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextPosition that = (TextPosition) o;

        return col == that.col && row == that.row;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return row + ":" + col;
    }
}
