package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

public class ParsingPosition {
    @NotNull
    private final TextPosition position;
    @NotNull
    private TokenStack<?> tokenStack;

    public ParsingPosition(@NotNull TextPosition position, @NotNull TokenStack<?> tokenStack) {
        this.position = position;
        this.tokenStack = tokenStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        if (!position.equals(that.position)) return false;
        if (!tokenStack.equals(that.tokenStack)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + tokenStack.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " position " + position +
                ", current token is " + tokenStack.previous() + " and remaining tokens are " + tokenStack.remaining();
    }
}
