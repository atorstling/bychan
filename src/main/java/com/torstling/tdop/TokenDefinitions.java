package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TokenDefinitions<N extends Node> {
    @NotNull
    private final List<TokenDefinition<N>> tokenDefinitions;


    public TokenDefinitions() {
        this(Collections.<TokenDefinition<N>>emptyList());
    }

    public TokenDefinitions(@NotNull final Collection<TokenDefinition<N>> tokens) {
        this.tokenDefinitions = new ArrayList<>(tokens);
    }
}
