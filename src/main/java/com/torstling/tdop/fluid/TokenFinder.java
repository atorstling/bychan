package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

public interface TokenFinder<N extends AstNode, S> {
    @NotNull
    GenericTokenType<N,S> getTokenTypeFor(@NotNull final TokenDefinition<N, S> definition);
}
