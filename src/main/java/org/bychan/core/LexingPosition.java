package org.bychan.core;

import org.jetbrains.annotations.NotNull;

public class LexingPosition {
    private final int streamPosition;
    @NotNull
    private final String remainingText;

    public LexingPosition(final int streamPosition, @NotNull final String remainingText) {
        this.streamPosition = streamPosition;
        this.remainingText = remainingText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingPosition that = (LexingPosition) o;

        return streamPosition == that.streamPosition && remainingText.equals(that.remainingText);
    }

    @Override
    public int hashCode() {
        return 31 * streamPosition + remainingText.hashCode();
    }

    @Override
    public String toString() {
        return "LexingPosition{" +
                "streamPosition=" + streamPosition +
                ", remainingText='" + remainingText + '\'' +
                '}';
    }
}
