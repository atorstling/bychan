package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;

public class ParsingFailedException extends RuntimeException {
    @NotNull
    private final LexParsingFailedInformation lexParsingFailedInformation;

    public ParsingFailedException(@NotNull LexParsingFailedInformation lexParsingFailedInformation) {
        super(lexParsingFailedInformation.toString());
        this.lexParsingFailedInformation = lexParsingFailedInformation;
    }

    public static ParsingFailedException forFailedAfterLexing(@NotNull String failureMessage, @NotNull TokenParserCallback<?> parser) {
        return new ParsingFailedException(LexParsingFailedInformation.forFailedAfterLexing(failureMessage, parser.getParsingPosition()));
    }

    @NotNull
    public LexParsingFailedInformation getLexParsingFailedInformation() {
        return lexParsingFailedInformation;
    }
}
