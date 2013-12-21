package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

public class LexingFailedException extends RuntimeException {
    public LexingFailedException(@NotNull final String message) {
        super(message);
    }
}
