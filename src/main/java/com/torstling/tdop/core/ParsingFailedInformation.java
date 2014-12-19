package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParsingFailedInformation {
    public static ParsingFailedInformation forFailedAfterLexing(@NotNull String failureMessage, @NotNull final ParsingPosition parsingPosition) {
        return new ParsingFailedInformation(null, new FailedAfterLexingInformation(failureMessage, parsingPosition));
    }

    @Nullable
    private final LexingFailedInformation lexingFailedInformation;
    @Nullable
    private final FailedAfterLexingInformation failedAfterLexingInformation;

    private ParsingFailedInformation(@Nullable LexingFailedInformation lexingFailedInformation, @Nullable FailedAfterLexingInformation failedAfterLexingInformation) {
        this.lexingFailedInformation = lexingFailedInformation;
        this.failedAfterLexingInformation = failedAfterLexingInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingFailedInformation that = (ParsingFailedInformation) o;

        if (failedAfterLexingInformation != null ? !failedAfterLexingInformation.equals(that.failedAfterLexingInformation) : that.failedAfterLexingInformation != null)
            return false;
        if (lexingFailedInformation != null ? !lexingFailedInformation.equals(that.lexingFailedInformation) : that.lexingFailedInformation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lexingFailedInformation != null ? lexingFailedInformation.hashCode() : 0;
        result = 31 * result + (failedAfterLexingInformation != null ? failedAfterLexingInformation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParsingFailedInformation{" +
                "lexingFailedInformation=" + lexingFailedInformation +
                ", failedAfterLexingInformation=" + failedAfterLexingInformation +
                '}';
    }
}
