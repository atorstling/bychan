package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

public class ParsingFailedInformation {
    @NotNull
    private final String failureMessage;
    @NotNull
    private final ParsingPosition parsingPosition;

    public ParsingFailedInformation(@NotNull String failureMessage, @NotNull final ParsingPosition parsingPosition) {
        this.failureMessage = failureMessage;
        this.parsingPosition = parsingPosition;
    }

    @Override
    public String toString() {
        return "Parsing terminated at " + parsingPosition + ": " + failureMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingFailedInformation that = (ParsingFailedInformation) o;

        if (!failureMessage.equals(that.failureMessage)) return false;
        if (!parsingPosition.equals(that.parsingPosition)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = failureMessage.hashCode();
        result = 31 * result + parsingPosition.hashCode();
        return result;
    }
}
