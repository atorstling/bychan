package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface StandaloneAstBuilder<N extends AstNode, S> {
    @NotNull
    N build(@NotNull S parent, @NotNull LexingMatch match);
}
