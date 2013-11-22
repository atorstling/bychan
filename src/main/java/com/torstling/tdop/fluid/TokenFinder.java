package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

public interface TokenFinder<N extends AstNode> {
    @NotNull
    DefinitionTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}