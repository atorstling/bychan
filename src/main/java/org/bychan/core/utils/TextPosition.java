package org.bychan.core.utils;

public class TextPosition {
    private final int row;
    private final int col;
    private final int index;

    public TextPosition(int index, int row, int col) {
        if (row < 1) {
            throw new IllegalArgumentException("Row must be >0, was " + row);
        }
        if (row < 1) {
            throw new IllegalArgumentException("Col must be >0, was " + col);
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index must be >=0, was" + index);
        }
        this.index = index;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextPosition that = (TextPosition) o;

        return col == that.col && index == that.index && row == that.row;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + index;
        return result;
    }

    @Override
    public String toString() {
        return row + ":" + col + " (index " + index + ")";
    }
}
