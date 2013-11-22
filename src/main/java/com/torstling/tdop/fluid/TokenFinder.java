package com.torstling.tdop.fluid;

import com.torstling.tdop.AstNode;
import com.torstling.tdop.fluid.DefinitionTokenType;
import com.torstling.tdop.fluid.TokenDefinition;
import org.jetbrains.annotations.NotNull;

public interface TokenFinder<N extends AstNode> {
    @NotNull
    DefinitionTokenType<N> getTokenTypeFor(@NotNull final TokenDefinition<N> definition);
}
