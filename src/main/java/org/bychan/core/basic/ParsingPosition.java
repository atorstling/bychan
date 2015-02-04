package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

public class ParsingPosition {
    @NotNull
    private final TextPosition textPosition;
    @NotNull
    private final LexemeStack<?> lexemeStack;

    public ParsingPosition(@NotNull TextPosition textPosition, @NotNull LexemeStack<?> lexemeStack) {
        this.textPosition = textPosition;
        this.lexemeStack = lexemeStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        return textPosition.equals(that.textPosition) && lexemeStack.equals(that.lexemeStack);

    }

    @Override
    public int hashCode() {
        int result = textPosition.hashCode();
        result = 31 * result + lexemeStack.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " position " + textPosition +
                ", current lexeme is " + lexemeStack.left() + " and remaining lexemes are " + lexemeStack.remaining();
    }

    @NotNull
    public TextPosition getTextPosition() {
        return textPosition;
    }
}
