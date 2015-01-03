package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

public class ParsingPosition {
    @NotNull
    private final TextPosition textPosition;
    @NotNull
    private final TokenStack<?> tokenStack;

    public ParsingPosition(@NotNull TextPosition textPosition, @NotNull TokenStack<?> tokenStack) {
        this.textPosition = textPosition;
        this.tokenStack = tokenStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        return textPosition.equals(that.textPosition) && tokenStack.equals(that.tokenStack);

    }

    @Override
    public int hashCode() {
        int result = textPosition.hashCode();
        result = 31 * result + tokenStack.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " position " + textPosition +
                ", current token is " + tokenStack.previous() + " and remaining tokens are " + tokenStack.remaining();
    }

    @NotNull
    public TextPosition getTextPosition() {
        return textPosition;
    }
}
