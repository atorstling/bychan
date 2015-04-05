package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

public class ParsingFailedInformation implements FailureInformation {
    @NotNull
    private final String failureMessage;
    @NotNull
    private final ParsingPosition parsingPosition;

    public ParsingFailedInformation(@NotNull String failureMessage, @NotNull ParsingPosition parsingPosition) {
        this.failureMessage = failureMessage;
        this.parsingPosition = parsingPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingFailedInformation that = (ParsingFailedInformation) o;

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

    @NotNull
    public String getFailureMessage() {
        return failureMessage;
    }

    @NotNull
    public ParsingPosition getParsingPosition() {
        return parsingPosition;
    }

    @NotNull
    @Override
    public TextPosition getTextPosition() {
        return parsingPosition.getTextPosition();
    }

    @NotNull
    @Override
    public ParsingFailedInformation toParsingFailedInformation() {
        return this;
    }
}
