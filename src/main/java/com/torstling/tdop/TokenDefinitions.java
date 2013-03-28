package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.*;

public class TokenDefinitions<N extends Node> implements Iterable<TokenDefinition<N>> {
    @NotNull
    private final List<TokenDefinition<N>> tokenDefinitions;


    public TokenDefinitions() {
        this(Collections.<TokenDefinition<N>>emptyList());
    }

    public TokenDefinitions(@NotNull final Collection<TokenDefinition<N>> tokens) {
        this.tokenDefinitions = new ArrayList<>(tokens);
    }

    @Override
    public Iterator<TokenDefinition<N>> iterator() {
        return tokenDefinitions.iterator();
    }
}
