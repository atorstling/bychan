package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class Language<N extends Node> {
    @NotNull
    private final List<TokenDefinitions<N>> tokenDefinitions;

    public Language(@NotNull final List<TokenDefinitions<N>> tokenDefinitions) {
        this.tokenDefinitions = tokenDefinitions;
    }

    public GenericParser getParser() {
        return new GenericParser(tokenDefinitions);
    }
}
