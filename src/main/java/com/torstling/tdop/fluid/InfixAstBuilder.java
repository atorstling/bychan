package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import org.jetbrains.annotations.NotNull;

public interface InfixAstBuilder<N extends AstNode> {
    N build(@NotNull LexingMatch match, @NotNull N left, @NotNull ParserCallback2<N> parser);
}
