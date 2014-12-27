package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

public class ParsingPosition {
    private final int position;
    @NotNull
    private TokenStack<?> tokenStack;

    public ParsingPosition(int position, @NotNull TokenStack<?> tokenStack) {
        this.position = position;
        this.tokenStack = tokenStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        return position == that.position && tokenStack.equals(that.tokenStack);
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + tokenStack.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " index " + position +
                ", current token is " + tokenStack.previous() + " and remaining tokens are " + tokenStack.remaining();
    }
}
