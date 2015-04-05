package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LexingPosition<N> {
    @NotNull
    private final TextPosition textPosition;
    @NotNull
    private final String remainingText;
    @Nullable
    private final Lexeme<N> lastLexeme;

    public LexingPosition(@NotNull final TextPosition textPosition, @NotNull final String remainingText, @Nullable final Lexeme<N> lastLexeme) {
        this.textPosition = textPosition;
        this.remainingText = remainingText;
        this.lastLexeme = lastLexeme;
    }


    @Override
    public String toString() {
        String lexemeText = lastLexeme == null ? "<none>" : "'" + lastLexeme.toString() + "'";
        return " position " + textPosition +
                ": last lexeme was " + lexemeText + ", remaining text is '" + remainingText + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingPosition that = (LexingPosition) o;

        return remainingText.equals(that.remainingText) && textPosition.equals(that.textPosition);

    }

    @Override
    public int hashCode() {
        int result = textPosition.hashCode();
        result = 31 * result + remainingText.hashCode();
        return result;
    }

    @NotNull
    public TextPosition getTextPosition() {
        return textPosition;
    }
}
