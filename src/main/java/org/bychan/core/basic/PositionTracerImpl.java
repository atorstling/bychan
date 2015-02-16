package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PositionTracerImpl<N> implements PositionTracer<N> {
    private final String originalInputString;

    public PositionTracerImpl(@NotNull String originalInputString) {
        this.originalInputString = originalInputString;
    }

    @NotNull
    @Override
    public ParsingPosition getParsingPosition(@NotNull LexemeStack<N> lexemeStack) {
        Lexeme<N> current = lexemeStack.history(0);
        final TextPosition textPosition = getTextPosition(current);
        return new ParsingPosition(textPosition, lexemeStack);
    }

    @NotNull
    private TextPosition getTextPosition(@Nullable Lexeme<N> current) {
        if (originalInputString.isEmpty()) {
            return new TextPosition(0,1,1);
        }
        final int positionToUse = getPositionToUse(current);
        return StringUtils.getTextPosition(originalInputString, positionToUse);
    }

    private int getPositionToUse(@Nullable Lexeme<N> current) {
        //Null means that we haven't started parsing yet. In this case we use the first string position.
        if (current == null) {
            return 0;
        }
        final int currentPosition = current.getMatch().getStartPosition();
        if (currentPosition == originalInputString.length()) {
            assert current.getToken().equals(EndToken.get());
            //If we are at the END token (past the input length), use the last position.
            return originalInputString.length() -1;
        }
        return currentPosition;
    }

}
