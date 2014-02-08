package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

public class ParsingFailedInformation {
    @NotNull
    private final String failureMessage;
    @NotNull
    private final LexingMatch match;

    public ParsingFailedInformation(@NotNull String failureMessage, @NotNull LexingMatch match) {
        this.failureMessage = failureMessage;
        this.match = match;
    }

    @Override
    public String toString() {
        return "Parsing terminated at lexing match " + match + ": " + failureMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingFailedInformation that = (ParsingFailedInformation) o;

        return failureMessage.equals(that.failureMessage) && match.equals(that.match);
    }

    @Override
    public int hashCode() {
        int result = failureMessage.hashCode();
        result = 31 * result + match.hashCode();
        return result;
    }
}
