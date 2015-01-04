package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

class LexingFailedException extends RuntimeException {
    private final LexingPosition lexingPosition;

    public LexingFailedException(@NotNull final LexingPosition lexingPosition, @NotNull final String message) {
        super(message);
        this.lexingPosition = lexingPosition;
    }

    public LexingPosition getLexingPosition() {
        return lexingPosition;
    }

    @Override
    public String toString() {
        return super.getMessage() + " @ " + lexingPosition;
    }
}
