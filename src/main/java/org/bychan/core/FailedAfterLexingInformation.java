package org.bychan.core;

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

        return failureMessage.equals(that.failureMessage) && parsingPosition.equals(that.parsingPosition);

    }

    @Override
    public int hashCode() {
        int result = failureMessage.hashCode();
        result = 31 * result + parsingPosition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Parsing failed: '" + failureMessage + '\'' + " @ " + parsingPosition ;
    }
}
