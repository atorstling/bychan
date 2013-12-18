package com.torstling.tdop.core;

public class ParsingFailedException extends RuntimeException {
    public ParsingFailedException(String message, LexingMatch match) {
        super("Parsing terminated at lexing match " + match + ": " + message);
    }
}
