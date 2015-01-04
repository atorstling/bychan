package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

public class LexingPosition {
    @NotNull
    private final TextPosition textPosition;
    @NotNull
    private final String remainingText;

    public LexingPosition(@NotNull final TextPosition textPosition, @NotNull final String remainingText) {
        this.textPosition = textPosition;
        this.remainingText = remainingText;
    }


    @Override
    public String toString() {
        return " position " + textPosition +
                ", remaining text is '" + remainingText + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingPosition that = (LexingPosition) o;

        if (!remainingText.equals(that.remainingText)) return false;
        if (!textPosition.equals(that.textPosition)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = textPosition.hashCode();
        result = 31 * result + remainingText.hashCode();
        return result;
    }
}
