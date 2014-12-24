package org.bychan.core;

import org.jetbrains.annotations.NotNull;

public class ParsingFailedException extends RuntimeException {
    @NotNull
    private final ParsingFailedInformation parsingFailedInformation;

    public ParsingFailedException(@NotNull ParsingFailedInformation parsingFailedInformation) {
        super(parsingFailedInformation.toString());
        this.parsingFailedInformation = parsingFailedInformation;
    }

    public ParsingFailedException(@NotNull String failureMessage, @NotNull TokenParserCallback<?> parser) {
        this(ParsingFailedInformation.forFailedAfterLexing(failureMessage, parser.getParsingPosition()));
    }

    @NotNull
    public ParsingFailedInformation getParsingFailedInformation() {
        return parsingFailedInformation;
    }
}
