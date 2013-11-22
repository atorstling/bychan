package com.torstling.tdop.fluid;

import com.torstling.tdop.AstNode;
import com.torstling.tdop.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface StandaloneAstBuilder<N extends AstNode> {
    N build(@NotNull LexingMatch match);
}
