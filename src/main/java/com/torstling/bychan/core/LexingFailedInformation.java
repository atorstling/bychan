package com.torstling.bychan.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
public class LexingFailedInformation {
    @NotNull
    private final String message;
    @NotNull
    private final LexingPosition lexingPosition;

    public LexingFailedInformation(@NotNull final String message, @NotNull final LexingPosition lexingPosition) {
        this.message = message;
        this.lexingPosition = lexingPosition;
    }

    @Override
    public String toString() {
        return "LexingFailedInformation{" +
                "message='" + message + '\'' +
                ", lexingPosition=" + lexingPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingFailedInformation that = (LexingFailedInformation) o;

        return lexingPosition.equals(that.lexingPosition) && message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + lexingPosition.hashCode();
        return result;
    }
}
