package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

public class PositionTracerImpl<N> implements PositionTracer<N> {
    private String originalInputString;

    public PositionTracerImpl(@NotNull String originalInputString) {
        this.originalInputString = originalInputString;
    }

    @NotNull
    @Override
    public ParsingPosition getParsingPosition(@NotNull TokenStack<N> tokenStack) {
            Token<N> previous = tokenStack.previous();
            final int startPosition;
            if (previous == null) {
                startPosition = 0;
            } else if (previous.getType().equals(EndTokenType.get())) {
                //Since the end token is past the end of the input text we have to use the position directly before
                //the end token.
                LexingMatch match = previous.getMatch();
                startPosition = match.getStartPosition() - 1;
            } else {
                LexingMatch match = previous.getMatch();
                startPosition = match.getStartPosition();
            }
            return new ParsingPosition(StringUtils.getTextPosition(originalInputString, startPosition), tokenStack);
    }
}
