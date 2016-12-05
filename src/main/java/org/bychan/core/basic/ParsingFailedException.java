package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

public class ParsingFailedException extends RuntimeException {
    @NotNull
    private final FailureInformation failureInformation;

    public ParsingFailedException(@NotNull FailureInformation failureInformation) {
        super(failureInformation.toString());
        this.failureInformation = failureInformation;
    }

    public static ParsingFailedException forFailedAfterLexing(@NotNull String failureMessage, @NotNull Parser<?> parser) {
        return new ParsingFailedException(new ParsingFailedInformation(failureMessage, parser.getParsingPosition()));
    }

    @NotNull
    public FailureInformation getFailureInformation() {
        return failureInformation;
    }
}
