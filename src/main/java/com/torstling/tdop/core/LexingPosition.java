package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
public class LexingPosition {
    private final int streamPosition;
    @NotNull
    private final String remainingText;

    public LexingPosition(final int streamPosition, @NotNull final String remainingText) {
        this.streamPosition = streamPosition;
        this.remainingText = remainingText;
    }

    public int getStreamPosition() {
        return streamPosition;
    }

    @NotNull
    public String getRemainingText() {
        return remainingText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingPosition that = (LexingPosition) o;

        if (streamPosition != that.streamPosition) return false;
        if (!remainingText.equals(that.remainingText)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = streamPosition;
        result = 31 * result + remainingText.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LexingPosition{" +
                "streamPosition=" + streamPosition +
                ", remainingText='" + remainingText + '\'' +
                '}';
    }
}
