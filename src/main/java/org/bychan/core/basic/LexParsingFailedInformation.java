package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;
import org.jetbrains.annotations.NotNull;

public class LexParsingFailedInformation {
    @NotNull
    FailureInformation failureInformation;

    private LexParsingFailedInformation(@NotNull final FailureInformation failureInformation) {
        this.failureInformation = failureInformation;
    }

    public static LexParsingFailedInformation forFailedAfterLexing(@NotNull String failureMessage, @NotNull final ParsingPosition parsingPosition) {
        return new LexParsingFailedInformation(new ParsingFailedInformation(failureMessage, parsingPosition));
    }

    @NotNull
    public static LexParsingFailedInformation forFailedLexing(@NotNull final LexingFailedInformation lexingFailedInformation) {
        return new LexParsingFailedInformation(lexingFailedInformation);
    }

    @NotNull
    public ParsingFailedInformation getParsingFailedInformation() {
        if (!(failureInformation instanceof ParsingFailedInformation)) {
            throw new RuntimeException("Did not fail after lexing:" + failureInformation);
        }
        return (ParsingFailedInformation) failureInformation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexParsingFailedInformation that = (LexParsingFailedInformation) o;

        return failureInformation.equals(that.failureInformation);

    }

    @Override
    public int hashCode() {
        return failureInformation.hashCode();
    }

    @Override
    public String toString() {
        return failureInformation.toString();
    }

    @NotNull
    public TextPosition getTextPosition() {
        return failureInformation.getTextPosition();
    }
}
