package com.torstling.bychan.core;

import org.jetbrains.annotations.NotNull;

public class ParsingFailedException extends RuntimeException {
    @NotNull
    private final ParsingFailedInformation parsingFailedInformation;

    public ParsingFailedException(@NotNull ParsingFailedInformation parsingFailedInformation) {
        super(parsingFailedInformation.toString());
        this.parsingFailedInformation = parsingFailedInformation;
    }

    @NotNull
    public ParsingFailedInformation getParsingFailedInformation() {
        return parsingFailedInformation;
    }
}
