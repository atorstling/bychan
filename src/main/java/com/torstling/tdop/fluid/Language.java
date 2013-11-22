package com.torstling.tdop.fluid;

import com.torstling.tdop.Node;
import com.torstling.tdop.fluid.TokenDefinitions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Language<N extends Node> {
    @NotNull
    private final List<TokenDefinitions<N>> tokenDefinitions;

    public Language(@NotNull final List<TokenDefinitions<N>> tokenDefinitions) {
        this.tokenDefinitions = tokenDefinitions;
    }

    public GenericParser<N> getParser() {
        return new GenericParser<>(tokenDefinitions);
    }
}
