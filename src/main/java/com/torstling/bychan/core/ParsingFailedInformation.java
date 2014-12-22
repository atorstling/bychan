package com.torstling.bychan.core;

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

        return !(failedAfterLexingInformation != null ? !failedAfterLexingInformation.equals(that.failedAfterLexingInformation) : that.failedAfterLexingInformation != null) && !(lexingFailedInformation != null ? !lexingFailedInformation.equals(that.lexingFailedInformation) : that.lexingFailedInformation != null);

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

    @NotNull
    public static ParsingFailedInformation forFailedLexing(@NotNull final LexingFailedInformation lexingFailedInformation) {
        return new ParsingFailedInformation(lexingFailedInformation, null);
    }
}
