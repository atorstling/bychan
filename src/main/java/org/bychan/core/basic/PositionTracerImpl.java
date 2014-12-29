package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PositionTracerImpl<N> implements PositionTracer<N> {
    private String originalInputString;

    public PositionTracerImpl(@NotNull String originalInputString) {
        this.originalInputString = originalInputString;
    }

    @NotNull
    @Override
    public ParsingPosition getParsingPosition(@NotNull TokenStack<N> tokenStack) {
        Token<N> previous = tokenStack.previous();
        final TextPosition textPosition = getTextPosition(previous);
        return new ParsingPosition(textPosition, tokenStack);
    }

    @NotNull
    private TextPosition getTextPosition(@Nullable Token<N> previous) {
        //Null previous means that we haven't started parsing yet.
        final int currentPosition = previous == null ? -1 : getStartPosition(previous);
        if (currentPosition == -1) {
            //If we are before the beginning (haven't started parsing), return first position.
            return new TextPosition(0, 1, 1);
        }
        return StringUtils.getTextPosition(originalInputString, currentPosition);
    }

    private int getStartPosition(@NotNull Token<N> previous) {
        LexingMatch match = previous.getMatch();
        if (previous.getType().equals(EndTokenType.get())) {
            //Since the end token is past the end of the input text we use the position directly before
            //the end token.
            return match.getStartPosition() - 1;
        }
        return match.getStartPosition();
    }
}
