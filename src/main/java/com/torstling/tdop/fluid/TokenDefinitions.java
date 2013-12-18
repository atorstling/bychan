package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class TokenDefinitions<N extends AstNode> implements Iterable<TokenDefinition<N>> {
    @NotNull
    private final List<TokenDefinition<N>> tokenDefinitions;

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
