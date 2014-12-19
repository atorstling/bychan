package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2014-12-19.
 */
class FailedAfterLexingInformation {
    @NotNull
    private final String failureMessage;
    @NotNull
    private final ParsingPosition parsingPosition;

    public FailedAfterLexingInformation(@NotNull String failureMessage, @NotNull ParsingPosition parsingPosition) {
        this.failureMessage = failureMessage;
        this.parsingPosition = parsingPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FailedAfterLexingInformation that = (FailedAfterLexingInformation) o;

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

    @Override
    public String toString() {
        return "FailedAfterLexingInformation{" +
                "failureMessage='" + failureMessage + '\'' +
                ", parsingPosition=" + parsingPosition +
                '}';
    }
}
