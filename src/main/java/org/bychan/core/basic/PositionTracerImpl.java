package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
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
        final int startPosition = getStartPosition(previous);
        return new ParsingPosition(StringUtils.getTextPosition(originalInputString, startPosition), tokenStack);
    }

    private int getStartPosition(@Nullable Token<N> previous) {
        if (previous == null) {
            return 0;
        }
        LexingMatch match = previous.getMatch();
        if (previous.getType().equals(EndTokenType.get())) {
            //Since the end token is past the end of the input text we use the position directly before
            //the end token.
            return match.getStartPosition() - 1;
        }
        return match.getStartPosition();
    }
}
