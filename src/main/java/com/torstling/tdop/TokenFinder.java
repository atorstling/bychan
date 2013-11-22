package com.torstling.tdop;

import com.torstling.tdop.fluid.DefinitionTokenType;
import com.torstling.tdop.fluid.TokenDefinition;
import org.jetbrains.annotations.NotNull;

public interface TokenFinder<N extends Node> {
    @NotNull
    DefinitionTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}
