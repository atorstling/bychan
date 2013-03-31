package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

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

    public String toString() {
        return tokenDefinitions.toString();
    }
}
