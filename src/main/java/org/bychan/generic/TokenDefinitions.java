package org.bychan.generic;

import org.jetbrains.annotations.NotNull;

import java.util.*;

class TokenDefinitions<N> implements Iterable<TokenDefinition<N>> {
    @NotNull
    private final List<TokenDefinition<N>> tokenDefinitions;

    public TokenDefinitions(@NotNull final Collection<TokenDefinition<N>> tokens) {
        this.tokenDefinitions = new ArrayList<>(tokens);
    }

    public TokenDefinitions() {
        this(Collections.emptyList());
    }

    @Override
    public Iterator<TokenDefinition<N>> iterator() {
        return tokenDefinitions.iterator();
    }

    public String toString() {
        return tokenDefinitions.toString();
    }

    public void add(@NotNull TokenDefinition<N> tokenDefinition) {
        this.tokenDefinitions.add(tokenDefinition);
    }

    @NotNull
    public List<TokenDefinition<N>> getTokens() {
        return tokenDefinitions;
    }
}
